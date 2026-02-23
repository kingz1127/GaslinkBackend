package com.gaslink.api.modules.auth;
import com.gaslink.api.shared.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name = "refresh_tokens")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RefreshToken extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(name = "user_id", nullable = false) private UUID userId;
    @Column(nullable = false, unique = true) private String token;
    @Column(name = "expires_at", nullable = false) private Instant expiresAt;
    @Column(name = "is_revoked") private boolean isRevoked = false;
}