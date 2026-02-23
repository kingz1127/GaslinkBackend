package com.gaslink.api.modules.payment.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class InitiatePaymentRequest {
    @NotNull private UUID orderId;
    @Email @NotBlank private String email;
    @NotBlank private String callbackUrl;
}