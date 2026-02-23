package com.gaslink.api.modules.user.dto;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String fullName;
    private String avatarUrl;
    private String pushToken;
}