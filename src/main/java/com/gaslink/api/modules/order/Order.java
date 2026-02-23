package com.gaslink.api.modules.order;
import com.gaslink.api.shared.audit.AuditableEntity;
import com.gaslink.api.shared.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "orders")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(unique = true, nullable = false) private String reference;
    @Column(name = "customer_id") private UUID customerId;
    @Column(name = "vendor_id") private UUID vendorId;
    @Enumerated(EnumType.STRING) @Column(name = "service_type") private ServiceType serviceType;
    @Enumerated(EnumType.STRING) @Column(name = "status") private OrderStatus status = OrderStatus.PENDING;
    @Column(name = "cylinder_size_kg") private Double cylinderSizeKg;
    private int qty;
    @Column(name = "delivery_lat") private Double deliveryLat;
    @Column(name = "delivery_lng") private Double deliveryLng;
    @Column(name = "distance_km") private Double distanceKm;
    @Column(precision = 12, scale = 2) private BigDecimal subtotal;
    @Column(name = "delivery_fee", precision = 12, scale = 2) private BigDecimal deliveryFee;
    @Column(name = "platform_fee", precision = 12, scale = 2) private BigDecimal platformFee;
    @Column(precision = 12, scale = 2) private BigDecimal total;
    @Enumerated(EnumType.STRING) @Column(name = "payment_method") private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING) @Column(name = "payment_status") private PaymentStatus paymentStatus = PaymentStatus.UNPAID;
    @Column(name = "scheduled_at") private Instant scheduledAt;
}