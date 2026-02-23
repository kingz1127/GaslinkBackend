package com.gaslink.api.modules.vendor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VendorToggleRequest {
    @NotNull(message = "enabled flag is required")
    private Boolean enabled;
    private String reason; // required when disabling
}
