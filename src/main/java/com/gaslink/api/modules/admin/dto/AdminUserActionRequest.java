package com.gaslink.api.modules.admin.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class AdminUserActionRequest {
    @NotNull private UUID userId;
    @NotBlank private String action; // SUSPEND, ACTIVATE
    private String reason;
}