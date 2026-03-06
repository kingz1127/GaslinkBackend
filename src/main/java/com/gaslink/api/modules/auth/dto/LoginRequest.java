package com.gaslink.api.modules.auth.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank private String phoneOrEmail;

    public @NotBlank String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public void setPhoneOrEmail(@NotBlank String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    @NotBlank private String password;
}