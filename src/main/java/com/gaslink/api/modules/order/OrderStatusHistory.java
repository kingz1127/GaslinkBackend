package com.gaslink.api.modules.order;

import com.gaslink.api.shared.enums.OrderStatus;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "order_id")
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "changed_by")
    private UUID changedBy;

    private String note;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    // Default constructor
    public OrderStatusHistory() {}

    // All args constructor
    public OrderStatusHistory(UUID id, UUID orderId, OrderStatus status,
                              UUID changedBy, String note, Instant createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.changedBy = changedBy;
        this.note = note;
        this.createdAt = createdAt;
    }

    // Builder pattern
    public static OrderStatusHistoryBuilder builder() {
        return new OrderStatusHistoryBuilder();
    }

    public static class OrderStatusHistoryBuilder {
        private UUID id;
        private UUID orderId;
        private OrderStatus status;
        private UUID changedBy;
        private String note;
        private Instant createdAt = Instant.now();

        public OrderStatusHistoryBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public OrderStatusHistoryBuilder orderId(UUID orderId) {
            this.orderId = orderId;
            return this;
        }

        public OrderStatusHistoryBuilder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public OrderStatusHistoryBuilder changedBy(UUID changedBy) {
            this.changedBy = changedBy;
            return this;
        }

        public OrderStatusHistoryBuilder note(String note) {
            this.note = note;
            return this;
        }

        public OrderStatusHistoryBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public OrderStatusHistory build() {
            return new OrderStatusHistory(id, orderId, status, changedBy, note, createdAt);
        }
    }

    // Getters and Setters (keeping your existing ones)
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public UUID getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(UUID changedBy) {
        this.changedBy = changedBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStatusHistory that = (OrderStatusHistory) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OrderStatusHistory{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", status=" + status +
                ", changedBy=" + changedBy +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}