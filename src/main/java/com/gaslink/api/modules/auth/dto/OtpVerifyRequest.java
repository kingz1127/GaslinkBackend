package com.gaslink.api.modules.auth.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OtpVerifyRequest {
    @NotBlank private String phone;
    @NotBlank private String otpCode;
}