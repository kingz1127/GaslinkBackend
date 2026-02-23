package com.gaslink.api.modules.vendor.dto;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class NearbyVendorDto {
    private UUID id;
    private String businessName;
    private Double lat;
    private Double lng;
    private BigDecimal rating;
    private int totalReviews;
    private double distanceKm;
    private BigDecimal lowestPrice;
    private boolean isOpen;
}