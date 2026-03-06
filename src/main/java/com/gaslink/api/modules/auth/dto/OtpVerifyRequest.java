package com.gaslink.api.modules.auth.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OtpVerifyRequest {
    @NotBlank private String phone;
    @NotBlank private String otpCode;

    public @NotBlank String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank String phone) {
        this.phone = phone;
    }

    public @NotBlank String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(@NotBlank String otpCode) {
        this.otpCode = otpCode;
    }
}