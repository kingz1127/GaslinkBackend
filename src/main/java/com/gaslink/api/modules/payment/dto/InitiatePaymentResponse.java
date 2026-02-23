package com.gaslink.api.modules.payment.dto;
import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class InitiatePaymentResponse {
    private String authorizationUrl;
    private String accessCode;
    private String reference;
}