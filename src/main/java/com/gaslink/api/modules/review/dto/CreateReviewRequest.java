package com.gaslink.api.modules.review.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Data
public class CreateReviewRequest {
    @NotNull private UUID orderId;
    @Min(1) @Max(5) private int rating;
    private String comment;

    public @NotNull UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(@NotNull UUID orderId) {
        this.orderId = orderId;
    }

    @Min(1)
    @Max(5)
    public int getRating() {
        return rating;
    }

    public void setRating(@Min(1) @Max(5) int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}