package com.gaslink.api.modules.vendor.dto;
import com.gaslink.api.shared.enums.VerificationStatus;
import com.gaslink.api.shared.enums.VendorAccountStatus;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class VendorProfileDto {
    private UUID id;
    private String businessName;
    private String businessAddress;
    private String nin;
    private Double lat;
    private Double lng;
    private Double serviceRadiusKm;
    private VerificationStatus verificationStatus;
    private VendorAccountStatus accountStatus;
    private String accountDisabledReason;
    private boolean isOpen;
    private BigDecimal rating;
    private int totalReviews;
}