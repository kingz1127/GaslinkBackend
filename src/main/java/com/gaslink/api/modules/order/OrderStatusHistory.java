package com.gaslink.api.modules.order;
import com.gaslink.api.shared.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "order_status_history")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderStatusHistory {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "order_id") private UUID orderId;
    @Enumerated(EnumType.STRING) private OrderStatus status;
    @Column(name = "changed_by") private UUID changedBy;
    private String note;
    @Column(name = "created_at") private Instant createdAt = Instant.now();
}