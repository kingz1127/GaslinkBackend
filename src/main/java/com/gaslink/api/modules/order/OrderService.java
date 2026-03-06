package com.gaslink.api.modules.order;

import com.gaslink.api.modules.inventory.*;
import com.gaslink.api.modules.order.dto.*;
import com.gaslink.api.modules.vendor.*;
import com.gaslink.api.shared.enums.*;
import com.gaslink.api.shared.exception.*;
import com.gaslink.api.shared.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final VendorRepository vendorRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderReferenceGenerator referenceGenerator;
    private final PricingEngine pricingEngine;

    public OrderService(OrderRepository orderRepository, OrderStatusHistoryRepository historyRepository,
                        VendorRepository vendorRepository, InventoryRepository inventoryRepository,
                        OrderReferenceGenerator referenceGenerator, PricingEngine pricingEngine) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
        this.vendorRepository = vendorRepository;
        this.inventoryRepository = inventoryRepository;
        this.referenceGenerator = referenceGenerator;
        this.pricingEngine = pricingEngine;
    }

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

        Order order = new Order();
        order.setReference(referenceGenerator.generate());
        order.setCustomerId(customerId);
        order.setVendorId(vendor.getId());
        order.setServiceType(req.getServiceType());
        order.setCylinderSizeKg(req.getCylinderSizeKg());
        order.setQty(req.getQty());
        order.setDeliveryLat(req.getDeliveryLat());
        order.setDeliveryLng(req.getDeliveryLng());
        order.setDistanceKm(dist);
        order.setSubtotal(pricing.getSubtotal());
        order.setDeliveryFee(pricing.getDeliveryFee());
        order.setPlatformFee(pricing.getPlatformFee());
        order.setTotal(pricing.getTotal());
        order.setPaymentMethod(req.getPaymentMethod());
        order.setScheduledAt(req.getScheduledAt());

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

    // ADDED THIS METHOD (This fixes the error in OrderController)
    @Transactional
    public OrderDto cancelOrder(UUID orderId, UUID requesterId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Safety check: ensure only the customer who placed the order can cancel it
        if (!o.getCustomerId().equals(requesterId)) {
            throw new ForbiddenException("Access denied");
        }

        // Logic check: only pending orders can be cancelled
        if (o.getStatus() != OrderStatus.PENDING) {
            throw new BusinessRuleException("Only pending orders can be cancelled");
        }

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
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrderId(orderId);
        history.setStatus(status);
        history.setChangedBy(changedBy);
        history.setNote(note);
        historyRepository.save(history);
    }

    private OrderDto toDtoWithHistory(Order o) {
        OrderDto dto = toDto(o);
        List<StatusHistoryDto> history = historyRepository.findByOrderIdOrderByCreatedAtAsc(o.getId()).stream()
                .map(h -> new StatusHistoryDto(h.getStatus(), h.getChangedBy(), h.getNote(), h.getCreatedAt()))
                .collect(Collectors.toList());
        dto.setHistory(history);
        return dto;
    }

    private OrderDto toDto(Order o) {
        return new OrderDto(
                o.getId(), o.getReference(), o.getCustomerId(), o.getVendorId(),
                o.getServiceType(), o.getStatus(), o.getCylinderSizeKg(), o.getQty(),
                o.getDeliveryLat(), o.getDeliveryLng(), o.getDistanceKm(), o.getSubtotal(),
                o.getDeliveryFee(), o.getPlatformFee(), o.getTotal(), o.getPaymentMethod(),
                o.getPaymentStatus(), o.getScheduledAt(), o.getCreatedAt()
        );
    }

    private OrderSummaryDto toSummary(Order o) {
        return new OrderSummaryDto(
                o.getId(), o.getReference(), o.getStatus(), o.getServiceType(),
                o.getCylinderSizeKg(), o.getQty(), o.getTotal(), o.getCreatedAt()
        );
    }
}