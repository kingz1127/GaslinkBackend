package com.gaslink.api.modules.user.dto;
import com.gaslink.api.shared.enums.UserRole;
import lombok.*;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserProfileDto {
    private UUID id;
    private String fullName;
    private String phone;
    private String email;
    private UserRole role;
    private String avatarUrl;
}