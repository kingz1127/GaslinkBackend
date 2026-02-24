package com.gaslink.api.modules.notification.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationDto {
    private UUID id;
    private String type;
    private String title;
    private String body;
    private boolean isRead;
    private Instant createdAt;

    // 1. Manual No-Args Constructor
    public NotificationDto() {}

    // 2. Manual All-Args Constructor (This replaces the Builder)
    public NotificationDto(UUID id, String type, String title, String body, boolean isRead, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.body = body;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public NotificationDto(UUID id, String type, String title, String body, boolean read, LocalDateTime createdAt) {
    }

    // 3. Manual Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}