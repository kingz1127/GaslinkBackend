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
    @NotNull private Double deliveryLng;
    @NotNull private PaymentMethod paymentMethod;
    private Instant scheduledAt;
}