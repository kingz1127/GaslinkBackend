package com.gaslink.api.modules.vendor.dto;
import com.gaslink.api.shared.enums.VerificationStatus;
import lombok.Data;

@Data
public class UpdateVendorStatusRequest {
    private VerificationStatus verificationStatus;
    private String reason;
}