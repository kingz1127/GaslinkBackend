package com.gaslink.api.modules.review;

import com.gaslink.api.shared.audit.AuditableEntity;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "reviews")
public class Review extends AuditableEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "order_id", unique = true, nullable = false)
    private UUID orderId;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "vendor_id")
    private UUID vendorId;

    @Column(nullable = false)
    private int rating;

    private String comment;

    // Default constructor
    public Review() {}

    // All args constructor (excluding AuditableEntity fields)
    public Review(UUID id, UUID orderId, UUID customerId, UUID vendorId, int rating, String comment) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.vendorId = vendorId;
        this.rating = rating;
        this.comment = comment;
    }

    // Builder pattern
    public static ReviewBuilder builder() {
        return new ReviewBuilder();
    }

    public static class ReviewBuilder {
        private UUID id;
        private UUID orderId;
        private UUID customerId;
        private UUID vendorId;
        private int rating;
        private String comment;

        public ReviewBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ReviewBuilder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public ReviewBuilder customerId(UUID customerId) {
            this.customerId = customerId;
            return this;
        }

        public ReviewBuilder vendorId(UUID vendorId) {
            this.vendorId = vendorId;
            return this;
        }

        public ReviewBuilder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public ReviewBuilder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Review build() {
            return new Review(id, orderId, customerId, vendorId, rating, comment);
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;
        return id != null ? id.equals(review.id) : review.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", customerId=" + customerId +
                ", vendorId=" + vendorId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}