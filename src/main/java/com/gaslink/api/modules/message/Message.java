package com.gaslink.api.modules.message;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID orderId;
    private UUID senderId;
    private String content;
    private Instant sentAt;

    public Message() {} // Manual constructor

    // MANUAL GETTERS
    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public UUID getSenderId() { return senderId; }
    public String getContent() { return content; }
    public Instant getSentAt() { return sentAt; }

    // MANUAL SETTERS
    public void setId(UUID id) { this.id = id; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public void setSenderId(UUID senderId) { this.senderId = senderId; }
    public void setContent(String content) { this.content = content; }
    public void setSentAt(Instant sentAt) { this.sentAt = sentAt; }
}