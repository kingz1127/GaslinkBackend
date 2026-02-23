package com.gaslink.api.modules.inventory;
import com.gaslink.api.shared.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity @Table(name = "cylinder_inventory")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CylinderInventory extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "vendor_id", nullable = false) private UUID vendorId;
    @Column(name = "size_kg", nullable = false) private Double sizeKg;
    @Column(name = "available_qty") private int availableQty = 0;
    @Column(name = "refill_price", precision = 12, scale = 2) private BigDecimal refillPrice;
    @Column(name = "exchange_price", precision = 12, scale = 2) private BigDecimal exchangePrice;
    @Column(name = "rental_price_per_day", precision = 12, scale = 2) private BigDecimal rentalPricePerDay;
    @Column(name = "purchase_price", precision = 12, scale = 2) private BigDecimal purchasePrice;
}