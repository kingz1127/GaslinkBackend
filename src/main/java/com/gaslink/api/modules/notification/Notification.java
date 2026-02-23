package com.gaslink.api.modules.notification;
import com.gaslink.api.shared.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "notifications")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Notification extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    private String type;
    @Column(nullable = false) private String title;
    @Column(nullable = false) private String body;
    @Column(columnDefinition = "TEXT") private String data;
    @Column(name = "is_read") private boolean isRead = false;
}