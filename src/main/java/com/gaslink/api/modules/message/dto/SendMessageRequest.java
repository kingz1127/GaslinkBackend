package com.gaslink.api.modules.message.dto;

import java.util.UUID;

public class SendMessageRequest {
    private UUID orderId;
    private String content;

    // MANUAL GETTERS (This fixes the errors in MessageService.sendMessage)
    public UUID getOrderId() { return orderId; }
    public String getContent() { return content; }

    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public void setContent(String content) { this.content = content; }
}