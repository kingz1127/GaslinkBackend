package com.gaslink.api.modules.order.dto;
import com.gaslink.api.shared.enums.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderDto {
    private UUID id;
    private String reference;
    private UUID customerId;
    private UUID vendorId;
    private ServiceType serviceType;
    private OrderStatus status;
    private Double cylinderSizeKg;
    private int qty;
    private Double deliveryLat;
    private Double deliveryLng;
    private Double distanceKm;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal platformFee;
    private BigDecimal total;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private Instant scheduledAt;
    private Instant createdAt;
    private List<StatusHistoryDto> history;
}