package com.gaslink.api.modules.vendor.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VendorAccountActionRequest {
    @NotBlank private String reason;

    public @NotBlank String getReason() {
        return reason;
    }

    public void setReason(@NotBlank String reason) {
        this.reason = reason;
    }
}