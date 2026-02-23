package com.gaslink.api.modules.subscription.dto;
import com.gaslink.api.shared.enums.SubscriptionStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SubscriptionDto {
    private UUID id;
    private UUID vendorId;
    private String plan;
    private BigDecimal amount;
    private String billingCycle;
    private SubscriptionStatus status;
    private Instant startedAt;
    private Instant expiresAt;
}