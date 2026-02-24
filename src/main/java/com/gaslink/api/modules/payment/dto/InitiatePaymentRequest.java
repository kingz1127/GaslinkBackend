package com.gaslink.api.modules.payment.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class InitiatePaymentRequest {
    @NotNull private UUID orderId;
    @Email @NotBlank private String email;

    public @NotNull UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(@NotNull UUID orderId) {
        this.orderId = orderId;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(@NotBlank String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @NotBlank private String callbackUrl;
}