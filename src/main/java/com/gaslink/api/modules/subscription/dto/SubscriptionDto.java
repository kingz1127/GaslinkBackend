package com.gaslink.api.modules.subscription.dto;

import com.gaslink.api.shared.enums.SubscriptionStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class SubscriptionDto {
    private UUID id;
    private UUID vendorId;
    private String plan;
    private BigDecimal amount;
    private String billingCycle;
    private SubscriptionStatus status;
    private Instant startedAt;
    private Instant expiresAt;

    // Default constructor
    public SubscriptionDto() {}

    // All args constructor
    public SubscriptionDto(UUID id, UUID vendorId, String plan, BigDecimal amount,
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
    public static SubscriptionDtoBuilder builder() {
        return new SubscriptionDtoBuilder();
    }

    public static class SubscriptionDtoBuilder {
        private UUID id;
        private UUID vendorId;
        private String plan;
        private BigDecimal amount;
        private String billingCycle;
        private SubscriptionStatus status;
        private Instant startedAt;
        private Instant expiresAt;

        public SubscriptionDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public SubscriptionDtoBuilder vendorId(UUID vendorId) {
            this.vendorId = vendorId;
            return this;
        }

        public SubscriptionDtoBuilder plan(String plan) {
            this.plan = plan;
            return this;
        }

        public SubscriptionDtoBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public SubscriptionDtoBuilder billingCycle(String billingCycle) {
            this.billingCycle = billingCycle;
            return this;
        }

        public SubscriptionDtoBuilder status(SubscriptionStatus status) {
            this.status = status;
            return this;
        }

        public SubscriptionDtoBuilder startedAt(Instant startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public SubscriptionDtoBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public SubscriptionDto build() {
            return new SubscriptionDto(id, vendorId, plan, amount, billingCycle,
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
}