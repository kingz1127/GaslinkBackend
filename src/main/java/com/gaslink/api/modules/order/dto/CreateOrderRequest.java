package com.gaslink.api.modules.order.dto;
import com.gaslink.api.shared.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class CreateOrderRequest {
    @NotNull private UUID vendorId;
    @NotNull private ServiceType serviceType;
    @NotNull @DecimalMin("1") private Double cylinderSizeKg;
    @Min(1) private int qty = 1;
    @NotNull private Double deliveryLat;

    public @NotNull UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(@NotNull UUID vendorId) {
        this.vendorId = vendorId;
    }

    public @NotNull ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(@NotNull ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public @NotNull @DecimalMin("1") Double getCylinderSizeKg() {
        return cylinderSizeKg;
    }

    public void setCylinderSizeKg(@NotNull @DecimalMin("1") Double cylinderSizeKg) {
        this.cylinderSizeKg = cylinderSizeKg;
    }

    @Min(1)
    public int getQty() {
        return qty;
    }

    public void setQty(@Min(1) int qty) {
        this.qty = qty;
    }

    public @NotNull Double getDeliveryLat() {
        return deliveryLat;
    }

    public void setDeliveryLat(@NotNull Double deliveryLat) {
        this.deliveryLat = deliveryLat;
    }

    public @NotNull Double getDeliveryLng() {
        return deliveryLng;
    }

    public void setDeliveryLng(@NotNull Double deliveryLng) {
        this.deliveryLng = deliveryLng;
    }

    public @NotNull PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(@NotNull PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Instant getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(Instant scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    @NotNull private Double deliveryLng;
    @NotNull private PaymentMethod paymentMethod;
    private Instant scheduledAt;
}