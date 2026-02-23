package com.gaslink.api.modules.review;
import com.gaslink.api.shared.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "reviews")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Review extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "order_id", unique = true, nullable = false) private UUID orderId;
    @Column(name = "customer_id") private UUID customerId;
    @Column(name = "vendor_id") private UUID vendorId;
    @Column(nullable = false) private int rating;
    private String comment;
}