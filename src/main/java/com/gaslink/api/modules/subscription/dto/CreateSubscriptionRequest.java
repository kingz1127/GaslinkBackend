package com.gaslink.api.modules.subscription.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateSubscriptionRequest {
    @NotBlank private String plan;
    @NotBlank private String billingCycle;
    @NotBlank private String paystackReference;
}