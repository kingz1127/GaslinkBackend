package com.gaslink.api.modules.admin.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class AdminUserActionRequest {
    @NotNull private UUID userId;
    @NotBlank private String action; // SUSPEND, ACTIVATE
    private String reason;

    public @NotNull UUID getUserId() {
        return userId;
    }

    public void setUserId(@NotNull UUID userId) {
        this.userId = userId;
    }

    public @NotBlank String getAction() {
        return action;
    }

    public void setAction(@NotBlank String action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}