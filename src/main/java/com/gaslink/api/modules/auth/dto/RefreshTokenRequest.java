package com.gaslink.api.modules.auth.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank private String refreshToken;

    public @NotBlank String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(@NotBlank String refreshToken) {
        this.refreshToken = refreshToken;
    }
}