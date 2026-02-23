package com.gaslink.api.modules.order;
import com.gaslink.api.modules.inventory.*;
import com.gaslink.api.modules.order.dto.*;
import com.gaslink.api.modules.vendor.*;
import com.gaslink.api.shared.enums.*;
import com.gaslink.api.shared.exception.*;
import com.gaslink.api.shared.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final VendorRepository vendorRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderReferenceGenerator referenceGenerator;
    private final PricingEngine pricingEngine;

    @Transactional
    public OrderDto createOrder(UUID customerId, CreateOrderRequest req) {
        Vendor vendor = vendorRepository.findById(req.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        if (vendor.getAccountStatus() == VendorAccountStatus.DISABLED)
            throw new BusinessRuleException("This vendor is currently unavailable");
        if (!vendor.isOpen()) throw new BusinessRuleException("Vendor is currently closed");

        CylinderInventory inv = inventoryRepository.findByVendorIdAndSizeKg(vendor.getId(), req.getCylinderSizeKg())
                .orElseThrow(() -> new ResourceNotFoundException("Cylinder size not available from this vendor"));
        if (inv.getAvailableQty() < req.getQty()) throw new BusinessRuleException("Insufficient stock");

        double dist = GeoUtils.haversineDistance(vendor.getLat(), vendor.getLng(), req.getDeliveryLat(), req.getDeliveryLng());
        BigDecimal unitPrice = getPrice(inv, req.getServiceType());
        PricingEngine.PricingBreakdown pricing = pricingEngine.calculate(unitPrice, req.getQty(), dist, req.getServiceType());

        Order order = Order.builder()
                .reference(referenceGenerator.generate())
                .customerId(customerId).vendorId(vendor.getId())
                .serviceType(req.getServiceType()).cylinderSizeKg(req.getCylinderSizeKg())
                .qty(req.getQty()).deliveryLat(req.getDeliveryLat()).deliveryLng(req.getDeliveryLng())
                .distanceKm(dist).subtotal(pricing.subtotal()).deliveryFee(pricing.deliveryFee())
                .platformFee(pricing.platformFee()).total(pricing.total())
                .paymentMethod(req.getPaymentMethod()).scheduledAt(req.getScheduledAt()).build();
        order = orderRepository.save(order);

        inv.setAvailableQty(inv.getAvailableQty() - req.getQty());
        inventoryRepository.save(inv);

        saveHistory(order.getId(), OrderStatus.PENDING, customerId, "Order created");
        return toDto(order);
    }

    public OrderDto getOrder(UUID orderId) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return toDtoWithHistory(o);
    }

    public List<OrderSummaryDto> getCustomerOrders(UUID customerId) {
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(this::toSummary).collect(Collectors.toList());
    }

    public List<OrderSummaryDto> getVendorOrders(UUID vendorId) {
        return orderRepository.findByVendorIdOrderByCreatedAtDesc(vendorId).stream()
                .map(this::toSummary).collect(Collectors.toList());
    }

    @Transactional
    public OrderDto updateStatus(UUID orderId, UUID changedBy, UpdateOrderStatusRequest req) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (o.getStatus() == OrderStatus.COMPLETED || o.getStatus() == OrderStatus.CANCELLED)
            throw new BusinessRuleException("Cannot update a completed or cancelled order");
        o.setStatus(req.getStatus());
        orderRepository.save(o);
        saveHistory(o.getId(), req.getStatus(), changedBy, req.getNote());
        return toDtoWithHistory(o);
    }

    @Transactional
    public OrderDto cancelOrder(UUID orderId, UUID requesterId) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (!o.getCustomerId().equals(requesterId)) throw new ForbiddenException("Access denied");
        if (o.getStatus() != OrderStatus.PENDING) throw new BusinessRuleException("Only pending orders can be cancelled");
        o.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(o);
        saveHistory(o.getId(), OrderStatus.CANCELLED, requesterId, "Cancelled by customer");
        return toDtoWithHistory(o);
    }

    private BigDecimal getPrice(CylinderInventory inv, ServiceType type) {
        return switch (type) {
            case PICKUP_REFILL -> inv.getRefillPrice();
            case EXCHANGE      -> inv.getExchangePrice();
            case RENTAL        -> inv.getRentalPricePerDay();
            case PURCHASE      -> inv.getPurchasePrice();
        };
    }

    private void saveHistory(UUID orderId, OrderStatus status, UUID changedBy, String note) {
        historyRepository.save(OrderStatusHistory.builder()
                .orderId(orderId).status(status).changedBy(changedBy).note(note).build());
    }

    private OrderDto toDtoWithHistory(Order o) {
        OrderDto dto = toDto(o);
        dto.setHistory(historyRepository.findByOrderIdOrderByCreatedAtAsc(o.getId()).stream()
                .map(h -> StatusHistoryDto.builder().status(h.getStatus()).changedBy(h.getChangedBy())
                        .note(h.getNote()).createdAt(h.getCreatedAt()).build()).collect(Collectors.toList()));
        return dto;
    }

    private OrderDto toDto(Order o) {
        return OrderDto.builder().id(o.getId()).reference(o.getReference())
                .customerId(o.getCustomerId()).vendorId(o.getVendorId())
                .serviceType(o.getServiceType()).status(o.getStatus())
                .cylinderSizeKg(o.getCylinderSizeKg()).qty(o.getQty())
                .deliveryLat(o.getDeliveryLat()).deliveryLng(o.getDeliveryLng())
                .distanceKm(o.getDistanceKm()).subtotal(o.getSubtotal())
                .deliveryFee(o.getDeliveryFee()).platformFee(o.getPlatformFee()).total(o.getTotal())
                .paymentMethod(o.getPaymentMethod()).paymentStatus(o.getPaymentStatus())
                .scheduledAt(o.getScheduledAt()).createdAt(o.getCreatedAt()).build();
    }

    private OrderSummaryDto toSummary(Order o) {
        return OrderSummaryDto.builder().id(o.getId()).reference(o.getReference())
                .status(o.getStatus()).serviceType(o.getServiceType())
                .cylinderSizeKg(o.getCylinderSizeKg()).qty(o.getQty())
                .total(o.getTotal()).createdAt(o.getCreatedAt()).build();
    }
}