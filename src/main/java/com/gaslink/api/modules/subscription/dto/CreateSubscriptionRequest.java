package com.gaslink.api.modules.subscription.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateSubscriptionRequest {
    @NotBlank private String plan;
    @NotBlank private String billingCycle;
    @NotBlank private String paystackReference;

    public @NotBlank String getPlan() {
        return plan;
    }

    public void setPlan(@NotBlank String plan) {
        this.plan = plan;
    }

    public @NotBlank String getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(@NotBlank String billingCycle) {
        this.billingCycle = billingCycle;
    }

    public @NotBlank String getPaystackReference() {
        return paystackReference;
    }

    public void setPaystackReference(@NotBlank String paystackReference) {
        this.paystackReference = paystackReference;
    }
}