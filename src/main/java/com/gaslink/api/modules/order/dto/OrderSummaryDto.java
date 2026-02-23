package com.gaslink.api.modules.order.dto;
import com.gaslink.api.shared.enums.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderSummaryDto {
    private UUID id;
    private String reference;
    private OrderStatus status;
    private ServiceType serviceType;
    private Double cylinderSizeKg;
    private int qty;
    private BigDecimal total;
    private Instant createdAt;
}