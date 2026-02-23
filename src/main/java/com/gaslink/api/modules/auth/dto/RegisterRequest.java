package com.gaslink.api.modules.auth.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank private String fullName;
    @NotBlank @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number") private String phone;
    @Email    private String email;
    @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") private String password;
    @NotBlank @Pattern(regexp = "^[0-9]{11}$", message = "NIN must be exactly 11 digits") private String nin;
    private Double lat;
    private Double lng;
}