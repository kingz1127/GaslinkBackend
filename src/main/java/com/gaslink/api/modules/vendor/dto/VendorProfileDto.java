package com.gaslink.api.modules.vendor.dto;

import com.gaslink.api.shared.enums.VerificationStatus;
import com.gaslink.api.shared.enums.VendorAccountStatus;
import java.math.BigDecimal;
import java.util.UUID;

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

    // Default constructor
    public VendorProfileDto() {}

    // All args constructor
    public VendorProfileDto(UUID id, String businessName, String businessAddress, String nin,
                            Double lat, Double lng, Double serviceRadiusKm,
                            VerificationStatus verificationStatus, VendorAccountStatus accountStatus,
                            String accountDisabledReason, boolean isOpen, BigDecimal rating, int totalReviews) {
        this.id = id;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.nin = nin;
        this.lat = lat;
        this.lng = lng;
        this.serviceRadiusKm = serviceRadiusKm;
        this.verificationStatus = verificationStatus;
        this.accountStatus = accountStatus;
        this.accountDisabledReason = accountDisabledReason;
        this.isOpen = isOpen;
        this.rating = rating;
        this.totalReviews = totalReviews;
    }

    // Builder pattern
    public static VendorProfileDtoBuilder builder() {
        return new VendorProfileDtoBuilder();
    }

    public static class VendorProfileDtoBuilder {
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

        public VendorProfileDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public VendorProfileDtoBuilder businessName(String businessName) {
            this.businessName = businessName;
            return this;
        }

        public VendorProfileDtoBuilder businessAddress(String businessAddress) {
            this.businessAddress = businessAddress;
            return this;
        }

        public VendorProfileDtoBuilder nin(String nin) {
            this.nin = nin;
            return this;
        }

        public VendorProfileDtoBuilder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public VendorProfileDtoBuilder lng(Double lng) {
            this.lng = lng;
            return this;
        }

        public VendorProfileDtoBuilder serviceRadiusKm(Double serviceRadiusKm) {
            this.serviceRadiusKm = serviceRadiusKm;
            return this;
        }

        public VendorProfileDtoBuilder verificationStatus(VerificationStatus verificationStatus) {
            this.verificationStatus = verificationStatus;
            return this;
        }

        public VendorProfileDtoBuilder accountStatus(VendorAccountStatus accountStatus) {
            this.accountStatus = accountStatus;
            return this;
        }

        public VendorProfileDtoBuilder accountDisabledReason(String accountDisabledReason) {
            this.accountDisabledReason = accountDisabledReason;
            return this;
        }

        public VendorProfileDtoBuilder isOpen(boolean isOpen) {
            this.isOpen = isOpen;
            return this;
        }

        public VendorProfileDtoBuilder rating(BigDecimal rating) {
            this.rating = rating;
            return this;
        }

        public VendorProfileDtoBuilder totalReviews(int totalReviews) {
            this.totalReviews = totalReviews;
            return this;
        }

        public VendorProfileDto build() {
            return new VendorProfileDto(id, businessName, businessAddress, nin, lat, lng,
                    serviceRadiusKm, verificationStatus, accountStatus,
                    accountDisabledReason, isOpen, rating, totalReviews);
        }
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getServiceRadiusKm() {
        return serviceRadiusKm;
    }

    public void setServiceRadiusKm(Double serviceRadiusKm) {
        this.serviceRadiusKm = serviceRadiusKm;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public VendorAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(VendorAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountDisabledReason() {
        return accountDisabledReason;
    }

    public void setAccountDisabledReason(String accountDisabledReason) {
        this.accountDisabledReason = accountDisabledReason;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }
}