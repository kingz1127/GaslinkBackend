package com.gaslink.api.modules.user.dto;

import com.gaslink.api.shared.enums.UserRole;
import java.util.UUID;

public class UserProfileDto {
    private UUID id;
    private String fullName;
    private String phone;
    private String email;
    private UserRole role;
    private String avatarUrl;

    // 1. MANUAL NO-ARGS CONSTRUCTOR (For JSON)
    public UserProfileDto() {}

    // 2. MANUAL ALL-ARGS CONSTRUCTOR (This replaces the Builder)
    public UserProfileDto(UUID id, String fullName, String phone, String email, UserRole role, String avatarUrl) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

    // ... Keep your manual Getters and Setters here ...
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}