package com.gaslink.api.modules.address.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateAddressRequest {
    @NotBlank private String label;
    @NotBlank private String addressLine;
    @NotBlank private String city;
    @NotBlank private String state;
    private Double lat;
    private Double lng;
    private boolean isDefault = false;
}