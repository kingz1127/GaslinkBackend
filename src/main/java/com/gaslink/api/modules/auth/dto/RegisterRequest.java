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

    public @NotBlank String getFullName() {
        return fullName;
    }

    public void setFullName(@NotBlank String fullName) {
        this.fullName = fullName;
    }

    public @NotBlank @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number") String phone) {
        this.phone = phone;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String password) {
        this.password = password;
    }

    public @NotBlank @Pattern(regexp = "^[0-9]{11}$", message = "NIN must be exactly 11 digits") String getNin() {
        return nin;
    }

    public void setNin(@NotBlank @Pattern(regexp = "^[0-9]{11}$", message = "NIN must be exactly 11 digits") String nin) {
        this.nin = nin;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}