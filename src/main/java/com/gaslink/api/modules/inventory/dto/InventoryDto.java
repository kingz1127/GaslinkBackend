package com.gaslink.api.modules.inventory.dto;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class InventoryDto {
    private UUID id;
    private UUID vendorId;
    private Double sizeKg;
    private int availableQty;
    private BigDecimal refillPrice;
    private BigDecimal exchangePrice;
    private BigDecimal rentalPricePerDay;
    private BigDecimal purchasePrice;
}