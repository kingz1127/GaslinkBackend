package com.gaslink.api.util;

import com.gaslink.api.shared.enums.ServiceType;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PricingEngine {

    private static final BigDecimal DELIVERY_FEE_PER_KM = new BigDecimal("50.00");  // NGN per km
    private static final BigDecimal PLATFORM_FEE_RATE = new BigDecimal("0.03");      // 3%
    private static final BigDecimal MIN_DELIVERY_FEE = new BigDecimal("200.00");

    private PricingEngine() {}

    public static PricingBreakdown calculate(PricingRequest request) {
        BigDecimal unitPrice = resolveUnitPrice(request);
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(request.getQuantity()));

        BigDecimal deliveryFee = DELIVERY_FEE_PER_KM
                .multiply(BigDecimal.valueOf(request.getDistanceKm()))
                .max(MIN_DELIVERY_FEE)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal platformFee = subtotal.multiply(PLATFORM_FEE_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(deliveryFee).add(platformFee);

        return PricingBreakdown.builder()
                .unitPrice(unitPrice)
                .subtotal(subtotal)
                .deliveryFee(deliveryFee)
                .platformFee(platformFee)
                .total(total)
                .build();
    }

    private static BigDecimal resolveUnitPrice(PricingRequest req) {
        ServiceType serviceType = req.getServiceType();

        if (serviceType == ServiceType.PICKUP_REFILL) {
            return req.getRefillPrice();
        } else if (serviceType == ServiceType.EXCHANGE) {
            return req.getExchangePrice();
        } else if (serviceType == ServiceType.RENTAL) {
            return req.getRentalPricePerDay().multiply(BigDecimal.valueOf(req.getRentalDays()));
        } else if (serviceType == ServiceType.PURCHASE) {
            return req.getPurchasePrice();
        } else {
            throw new IllegalArgumentException("Unknown service type: " + serviceType);
        }
    }

    public static class PricingRequest {
        private ServiceType serviceType;
        private int quantity;
        private double distanceKm;
        private BigDecimal refillPrice;
        private BigDecimal exchangePrice;
        private BigDecimal rentalPricePerDay;
        private int rentalDays;
        private BigDecimal purchasePrice;

        // Default constructor
        public PricingRequest() {}

        // All args constructor
        public PricingRequest(ServiceType serviceType, int quantity, double distanceKm,
                              BigDecimal refillPrice, BigDecimal exchangePrice,
                              BigDecimal rentalPricePerDay, int rentalDays,
                              BigDecimal purchasePrice) {
            this.serviceType = serviceType;
            this.quantity = quantity;
            this.distanceKm = distanceKm;
            this.refillPrice = refillPrice;
            this.exchangePrice = exchangePrice;
            this.rentalPricePerDay = rentalPricePerDay;
            this.rentalDays = rentalDays;
            this.purchasePrice = purchasePrice;
        }

        // Builder pattern
        public static PricingRequestBuilder builder() {
            return new PricingRequestBuilder();
        }

        public static class PricingRequestBuilder {
            private ServiceType serviceType;
            private int quantity;
            private double distanceKm;
            private BigDecimal refillPrice;
            private BigDecimal exchangePrice;
            private BigDecimal rentalPricePerDay;
            private int rentalDays;
            private BigDecimal purchasePrice;

            public PricingRequestBuilder serviceType(ServiceType serviceType) {
                this.serviceType = serviceType;
                return this;
            }

            public PricingRequestBuilder quantity(int quantity) {
                this.quantity = quantity;
                return this;
            }

            public PricingRequestBuilder distanceKm(double distanceKm) {
                this.distanceKm = distanceKm;
                return this;
            }

            public PricingRequestBuilder refillPrice(BigDecimal refillPrice) {
                this.refillPrice = refillPrice;
                return this;
            }

            public PricingRequestBuilder exchangePrice(BigDecimal exchangePrice) {
                this.exchangePrice = exchangePrice;
                return this;
            }

            public PricingRequestBuilder rentalPricePerDay(BigDecimal rentalPricePerDay) {
                this.rentalPricePerDay = rentalPricePerDay;
                return this;
            }

            public PricingRequestBuilder rentalDays(int rentalDays) {
                this.rentalDays = rentalDays;
                return this;
            }

            public PricingRequestBuilder purchasePrice(BigDecimal purchasePrice) {
                this.purchasePrice = purchasePrice;
                return this;
            }

            public PricingRequest build() {
                return new PricingRequest(serviceType, quantity, distanceKm, refillPrice,
                        exchangePrice, rentalPricePerDay, rentalDays, purchasePrice);
            }
        }

        // Getters and Setters
        public ServiceType getServiceType() {
            return serviceType;
        }

        public void setServiceType(ServiceType serviceType) {
            this.serviceType = serviceType;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getDistanceKm() {
            return distanceKm;
        }

        public void setDistanceKm(double distanceKm) {
            this.distanceKm = distanceKm;
        }

        public BigDecimal getRefillPrice() {
            return refillPrice;
        }

        public void setRefillPrice(BigDecimal refillPrice) {
            this.refillPrice = refillPrice;
        }

        public BigDecimal getExchangePrice() {
            return exchangePrice;
        }

        public void setExchangePrice(BigDecimal exchangePrice) {
            this.exchangePrice = exchangePrice;
        }

        public BigDecimal getRentalPricePerDay() {
            return rentalPricePerDay;
        }

        public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
            this.rentalPricePerDay = rentalPricePerDay;
        }

        public int getRentalDays() {
            return rentalDays;
        }

        public void setRentalDays(int rentalDays) {
            this.rentalDays = rentalDays;
        }

        public BigDecimal getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(BigDecimal purchasePrice) {
            this.purchasePrice = purchasePrice;
        }
    }

    public static class PricingBreakdown {
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
        private BigDecimal deliveryFee;
        private BigDecimal platformFee;
        private BigDecimal total;

        // Default constructor
        public PricingBreakdown() {}

        // All args constructor
        public PricingBreakdown(BigDecimal unitPrice, BigDecimal subtotal,
                                BigDecimal deliveryFee, BigDecimal platformFee,
                                BigDecimal total) {
            this.unitPrice = unitPrice;
            this.subtotal = subtotal;
            this.deliveryFee = deliveryFee;
            this.platformFee = platformFee;
            this.total = total;
        }

        // Builder pattern
        public static PricingBreakdownBuilder builder() {
            return new PricingBreakdownBuilder();
        }

        public static class PricingBreakdownBuilder {
            private BigDecimal unitPrice;
            private BigDecimal subtotal;
            private BigDecimal deliveryFee;
            private BigDecimal platformFee;
            private BigDecimal total;

            public PricingBreakdownBuilder unitPrice(BigDecimal unitPrice) {
                this.unitPrice = unitPrice;
                return this;
            }

            public PricingBreakdownBuilder subtotal(BigDecimal subtotal) {
                this.subtotal = subtotal;
                return this;
            }

            public PricingBreakdownBuilder deliveryFee(BigDecimal deliveryFee) {
                this.deliveryFee = deliveryFee;
                return this;
            }

            public PricingBreakdownBuilder platformFee(BigDecimal platformFee) {
                this.platformFee = platformFee;
                return this;
            }

            public PricingBreakdownBuilder total(BigDecimal total) {
                this.total = total;
                return this;
            }

            public PricingBreakdown build() {
                return new PricingBreakdown(unitPrice, subtotal, deliveryFee, platformFee, total);
            }
        }

        // Getters and Setters
        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }

        public BigDecimal getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(BigDecimal deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public BigDecimal getPlatformFee() {
            return platformFee;
        }

        public void setPlatformFee(BigDecimal platformFee) {
            this.platformFee = platformFee;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }
    }
}