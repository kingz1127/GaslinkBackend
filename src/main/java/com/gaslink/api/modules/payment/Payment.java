package com.gaslink.api.modules.payment;
import com.gaslink.api.shared.audit.AuditableEntity;
import com.gaslink.api.shared.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "payments")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Payment extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "order_id", nullable = false) private UUID orderId;
    private String gateway;
    @Column(name = "gateway_ref") private String gatewayRef;
    @Column(precision = 12, scale = 2) private BigDecimal amount;
    private String currency;
    @Enumerated(EnumType.STRING) private PaymentStatus status = PaymentStatus.UNPAID;
    @Column(name = "paid_at") private Instant paidAt;
    @Column(columnDefinition = "TEXT") private String metadata;
}