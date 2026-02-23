package com.gaslink.api.modules.message.dto;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MessageDto {
    private UUID id;
    private UUID orderId;
    private UUID senderId;
    private String content;
    private boolean isRead;
    private Instant createdAt;
}