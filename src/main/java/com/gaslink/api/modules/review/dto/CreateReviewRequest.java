package com.gaslink.api.modules.review.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class CreateReviewRequest {
    @NotNull private UUID orderId;
    @Min(1) @Max(5) private int rating;
    private String comment;
}