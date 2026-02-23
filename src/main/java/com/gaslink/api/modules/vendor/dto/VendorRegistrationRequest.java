package com.gaslink.api.modules.vendor.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VendorRegistrationRequest {
    @NotBlank private String businessName;
    @NotBlank private String businessAddress;
    @NotBlank @Pattern(regexp = "^[0-9]{11}$", message = "NIN must be exactly 11 digits") private String nin;
    @NotNull private Double lat;
    @NotNull private Double lng;
    @NotNull @Min(1) @Max(100) private Double serviceRadiusKm;
}