package com.gaslink.api.modules.address.dto;
import lombok.*;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AddressDto {
    private UUID id;
    private String label;
    private String addressLine;
    private String city;
    private String state;
    private Double lat;
    private Double lng;
    private boolean isDefault;
}