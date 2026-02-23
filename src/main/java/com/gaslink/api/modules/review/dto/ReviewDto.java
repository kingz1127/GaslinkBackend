package com.gaslink.api.modules.review.dto;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReviewDto {
    private UUID id;
    private UUID orderId;
    private UUID customerId;
    private UUID vendorId;
    private int rating;
    private String comment;
    private Instant createdAt;
}