package com.gaslink.api.modules.message;
import com.gaslink.api.shared.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "messages")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Message extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "order_id", nullable = false) private UUID orderId;
    @Column(name = "sender_id") private UUID senderId;
    @Column(nullable = false) private String content;
    @Column(name = "is_read") private boolean isRead = false;
}