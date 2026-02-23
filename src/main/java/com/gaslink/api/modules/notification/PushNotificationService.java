package com.gaslink.api.modules.notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service @RequiredArgsConstructor @Slf4j
public class PushNotificationService {
    private final WebClient.Builder webClientBuilder;

    @Async("pushNotificationExecutor")
    public void sendPushNotification(String pushToken, String title, String body) {
        if (pushToken == null || pushToken.isBlank()) return;
        try {
            Map<String,Object> message = Map.of(
                "to", pushToken, "title", title, "body", body, "sound", "default");
            webClientBuilder.build()
                .post().uri("https://exp.host/--/api/v2/push/send")
                .bodyValue(message).retrieve().bodyToMono(String.class).subscribe(
                    r -> log.info("Push sent to {}", pushToken),
                    e -> log.error("Push failed: {}", e.getMessage()));
        } catch (Exception e) {
            log.error("Push notification error: {}", e.getMessage());
        }
    }
}