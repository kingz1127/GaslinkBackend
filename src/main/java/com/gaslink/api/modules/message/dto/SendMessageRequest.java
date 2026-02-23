package com.gaslink.api.modules.message.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class SendMessageRequest {
    @NotNull private UUID orderId;
    @NotBlank private String content;
}