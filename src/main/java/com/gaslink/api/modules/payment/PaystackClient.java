package com.gaslink.api.modules.payment;
import com.gaslink.api.modules.payment.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.util.Map;

@Component
public class PaystackClient {
    private final WebClient webClient;
    private final String secretKey;

    public PaystackClient(@Value("${app.paystack.secret-key}") String secretKey) {
        this.secretKey = secretKey;
        this.webClient = WebClient.builder().baseUrl("https://api.paystack.co")
                .defaultHeader("Authorization", "Bearer " + secretKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public InitiatePaymentResponse initializeTransaction(String email, BigDecimal amountNgn, String reference, String callbackUrl) {
        Map<String,Object> body = Map.of(
            "email", email,
            "amount", amountNgn.multiply(BigDecimal.valueOf(100)).intValue(),
            "reference", reference,
            "callback_url", callbackUrl
        );
        Map resp = webClient.post().uri("/transaction/initialize")
                .bodyValue(body).retrieve().bodyToMono(Map.class).block();
        Map data = (Map) resp.get("data");
        return InitiatePaymentResponse.builder()
                .authorizationUrl((String) data.get("authorization_url"))
                .accessCode((String) data.get("access_code"))
                .reference((String) data.get("reference")).build();
    }

    public boolean verifyTransaction(String reference) {
        Map resp = webClient.get().uri("/transaction/verify/" + reference)
                .retrieve().bodyToMono(Map.class).block();
        Map data = (Map) resp.get("data");
        return "success".equals(data.get("status"));
    }
}