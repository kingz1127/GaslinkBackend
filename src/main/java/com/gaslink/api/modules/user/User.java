package com.gaslink.api.modules.user;
import com.gaslink.api.shared.audit.AuditableEntity;
import com.gaslink.api.shared.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity @Table(name = "users")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends AuditableEntity {
    @Id @GeneratedValue @Column(columnDefinition = "uuid") private UUID id;
    @Column(unique = true, nullable = false) private String phone;
    @Column(unique = true) private String email;
    @Column(name = "password_hash", nullable = false) private String passwordHash;
    @Column(name = "full_name", nullable = false) private String fullName;
    @Column(name = "avatar_url") private String avatarUrl;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private UserRole role;
    @Column(name = "is_active") private boolean isActive = false;
    @Column(name = "push_token") private String pushToken;
}