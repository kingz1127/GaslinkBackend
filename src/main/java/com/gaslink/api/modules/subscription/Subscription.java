package com.gaslink.api.modules.subscription;
import com.gaslink.api.shared.audit.AuditableEntity;
import com.gaslink.api.shared.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "subscriptions")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Subscription extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "vendor_id", nullable = false) private UUID vendorId;
    private String plan;
    @Column(precision = 12, scale = 2) private BigDecimal amount;
    @Column(name = "billing_cycle") private String billingCycle;
    @Enumerated(EnumType.STRING) private SubscriptionStatus status = SubscriptionStatus.ACTIVE;
    @Column(name = "started_at") private Instant startedAt;
    @Column(name = "expires_at") private Instant expiresAt;
}