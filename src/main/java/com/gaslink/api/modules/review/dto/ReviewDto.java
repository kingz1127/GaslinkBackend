package com.gaslink.api.modules.review.dto;

import java.time.Instant;
import java.util.UUID;

public class ReviewDto {
    private UUID id;
    private UUID orderId;
    private UUID customerId;
    private UUID vendorId;
    private int rating;
    private String comment;
    private Instant createdAt;

    // Default constructor
    public ReviewDto() {}

    // All args constructor
    public ReviewDto(UUID id, UUID orderId, UUID customerId, UUID vendorId,
                     int rating, String comment, Instant createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.vendorId = vendorId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Builder pattern
    public static ReviewDtoBuilder builder() {
        return new ReviewDtoBuilder();
    }

    public static class ReviewDtoBuilder {
        private UUID id;
        private UUID orderId;
        private UUID customerId;
        private UUID vendorId;
        private int rating;
        private String comment;
        private Instant createdAt;

        public ReviewDtoBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ReviewDtoBuilder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public ReviewDtoBuilder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public ReviewDtoBuilder vendorId(UUID vendorId) {
            this.vendorId = vendorId;
            return this;
        }

        public ReviewDtoBuilder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public ReviewDtoBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewDtoBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReviewDto build() {
            return new ReviewDto(id, orderId, customerId, vendorId, rating, comment, createdAt);
        }
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getVendorId() {
        return vendorId;
    }

    public void setVendorId(UUID vendorId) {
        this.vendorId = vendorId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}