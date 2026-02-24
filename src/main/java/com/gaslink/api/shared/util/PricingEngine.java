package com.gaslink.api.shared.util;

import com.gaslink.api.shared.enums.ServiceType;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PricingEngine {
    private static final BigDecimal DELIVERY_FEE_PER_KM = new BigDecimal("50");
    private static final BigDecimal PLATFORM_FEE_RATE   = new BigDecimal("0.05");

    public PricingBreakdown calculate(BigDecimal unitPrice, int qty, double distanceKm, ServiceType type) {
        BigDecimal subtotal     = unitPrice.multiply(BigDecimal.valueOf(qty));
        BigDecimal deliveryFee  = DELIVERY_FEE_PER_KM.multiply(BigDecimal.valueOf(distanceKm));
        BigDecimal platformFee  = subtotal.multiply(PLATFORM_FEE_RATE);
        BigDecimal total        = subtotal.add(deliveryFee).add(platformFee);

        return new PricingBreakdown(subtotal, deliveryFee, platformFee, total);
    }

    // Updated Record with explicit Getters to match OrderService requirements
    public record PricingBreakdown(
            BigDecimal subtotal,
            BigDecimal deliveryFee,
            BigDecimal platformFee,
            BigDecimal total
    ) {
        // These allow you to use pricing.getSubtotal() in your Service
        public BigDecimal getSubtotal() { return subtotal; }
        public BigDecimal getDeliveryFee() { return deliveryFee; }
        public BigDecimal getPlatformFee() { return platformFee; }
        public BigDecimal getTotal() { return total; }
    }
}