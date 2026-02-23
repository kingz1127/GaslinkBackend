package com.gaslink.api.modules.order.dto;
import com.gaslink.api.shared.enums.OrderStatus;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class StatusHistoryDto {
    private OrderStatus status;
    private UUID changedBy;
    private String note;
    private Instant createdAt;
}