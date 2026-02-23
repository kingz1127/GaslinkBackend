package com.gaslink.api.util;

import com.gaslink.api.shared.enums.ServiceType;
import lombok.Builder;
import lombok.Data;

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
        return switch (req.getServiceType()) {
            case PICKUP_REFILL -> req.getRefillPrice();
            case EXCHANGE -> req.getExchangePrice();
            case RENTAL -> req.getRentalPricePerDay().multiply(BigDecimal.valueOf(req.getRentalDays()));
            case PURCHASE -> req.getPurchasePrice();
        };
    }

    @Data
    @Builder
    public static class PricingRequest {
        private ServiceType serviceType;
        private int quantity;
        private double distanceKm;
        private BigDecimal refillPrice;
        private BigDecimal exchangePrice;
        private BigDecimal rentalPricePerDay;
        private int rentalDays;
        private BigDecimal purchasePrice;
    }

    @Data
    @Builder
    public static class PricingBreakdown {
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
        private BigDecimal deliveryFee;
        private BigDecimal platformFee;
        private BigDecimal total;
    }
}
