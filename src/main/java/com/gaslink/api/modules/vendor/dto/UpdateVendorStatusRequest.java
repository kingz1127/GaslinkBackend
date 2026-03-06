package com.gaslink.api.modules.vendor.dto;
import com.gaslink.api.shared.enums.VerificationStatus;
import lombok.Data;

@Data
public class UpdateVendorStatusRequest {
    private VerificationStatus verificationStatus;
    private String reason;

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}