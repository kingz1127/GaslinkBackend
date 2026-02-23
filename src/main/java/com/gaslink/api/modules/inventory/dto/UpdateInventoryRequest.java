package com.gaslink.api.modules.inventory.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class UpdateInventoryRequest {
    @NotNull @DecimalMin("0") private Double sizeKg;
    @Min(0) private int availableQty;
    private BigDecimal refillPrice;
    private BigDecimal exchangePrice;
    private BigDecimal rentalPricePerDay;
    private BigDecimal purchasePrice;
}