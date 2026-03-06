package com.gaslink.api.modules.payment;

import com.gaslink.api.shared.audit.AuditableEntity;
import com.gaslink.api.shared.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment extends AuditableEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    private String gateway;

    @Column(name = "gateway_ref")
    private String gatewayRef;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.UNPAID;

    @Column(name = "paid_at")
    private Instant paidAt;

    @Column(columnDefinition = "TEXT")
    private String metadata;

    // Default constructor
    public Payment() {}

    // All args constructor
    public Payment(UUID id, UUID orderId, String gateway, String gatewayRef,
                   BigDecimal amount, String currency, PaymentStatus status,
                   Instant paidAt, String metadata) {
        this.id = id;
        this.orderId = orderId;
        this.gateway = gateway;
        this.gatewayRef = gatewayRef;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.paidAt = paidAt;
        this.metadata = metadata;
    }

    // Builder pattern
    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public static class PaymentBuilder {
        private UUID id;
        private UUID orderId;
        private String gateway;
        private String gatewayRef;
        private BigDecimal amount;
        private String currency;
        private PaymentStatus status = PaymentStatus.UNPAID;
        private Instant paidAt;
        private String metadata;

        public PaymentBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public PaymentBuilder gateway(String gateway) {
            this.gateway = gateway;
            return this;
        }

        public PaymentBuilder gatewayRef(String gatewayRef) {
            this.gatewayRef = gatewayRef;
            return this;
        }

        public PaymentBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PaymentBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public PaymentBuilder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public PaymentBuilder paidAt(Instant paidAt) {
            this.paidAt = paidAt;
            return this;
        }

        public PaymentBuilder metadata(String metadata) {
            this.metadata = metadata;
            return this;
        }

        public Payment build() {
            return new Payment(id, orderId, gateway, gatewayRef, amount,
                    currency, status, paidAt, metadata);
        }
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getGatewayRef() {
        return gatewayRef;
    }

    public void setGatewayRef(String gatewayRef) {
        this.gatewayRef = gatewayRef;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;
        return id != null ? id.equals(payment.id) : payment.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", gateway='" + gateway + '\'' +
                ", gatewayRef='" + gatewayRef + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                ", paidAt=" + paidAt +
                '}';
    }
}