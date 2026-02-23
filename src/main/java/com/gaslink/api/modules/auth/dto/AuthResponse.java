package com.gaslink.api.modules.auth.dto;
import com.gaslink.api.modules.user.dto.UserProfileDto;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private UserProfileDto user;
}