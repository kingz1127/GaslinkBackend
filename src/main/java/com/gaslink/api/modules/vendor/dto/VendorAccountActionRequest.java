package com.gaslink.api.modules.vendor.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VendorAccountActionRequest {
    @NotBlank private String reason;
}