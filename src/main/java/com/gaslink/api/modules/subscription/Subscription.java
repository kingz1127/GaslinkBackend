package com.gaslink.api.modules.subscription;

import com.gaslink.api.shared.audit.AuditableEntity;
import com.gaslink.api.shared.enums.SubscriptionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "subscriptions")
public class Subscription extends AuditableEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "vendor_id", nullable = false)
    private UUID vendorId;

    private String plan;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "billing_cycle")
    private String billingCycle;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    // Default constructor
    public Subscription() {}

    // All args constructor
    public Subscription(UUID id, UUID vendorId, String plan, BigDecimal amount,
                        String billingCycle, SubscriptionStatus status,
                        Instant startedAt, Instant expiresAt) {
        this.id = id;
        this.vendorId = vendorId;
        this.plan = plan;
        this.amount = amount;
        this.billingCycle = billingCycle;
        this.status = status;
        this.startedAt = startedAt;
        this.expiresAt = expiresAt;
    }

    // Builder pattern
    public static SubscriptionBuilder builder() {
        return new SubscriptionBuilder();
    }

    public static class SubscriptionBuilder {
        private UUID id;
        private UUID vendorId;
        private String plan;
        private BigDecimal amount;
        private String billingCycle;
        private SubscriptionStatus status = SubscriptionStatus.ACTIVE;
        private Instant startedAt;
        private Instant expiresAt;

        public SubscriptionBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public SubscriptionBuilder vendorId(UUID vendorId) {
            this.vendorId = vendorId;
            return this;
        }

        public SubscriptionBuilder plan(String plan) {
            this.plan = plan;
            return this;
        }

        public SubscriptionBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public SubscriptionBuilder billingCycle(String billingCycle) {
            this.billingCycle = billingCycle;
            return this;
        }

        public SubscriptionBuilder status(SubscriptionStatus status) {
            this.status = status;
            return this;
        }

        public SubscriptionBuilder startedAt(Instant startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public SubscriptionBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Subscription build() {
            return new Subscription(id, vendorId, plan, amount, billingCycle,
                    status, startedAt, expiresAt);
        }
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", vendorId=" + vendorId +
                ", plan='" + plan + '\'' +
                ", amount=" + amount +
                ", billingCycle='" + billingCycle + '\'' +
                ", status=" + status +
                ", startedAt=" + startedAt +
                ", expiresAt=" + expiresAt +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}