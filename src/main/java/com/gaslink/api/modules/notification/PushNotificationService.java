package com.gaslink.api.modules.notification;

import org.slf4j.Logger; // Added manual import
import org.slf4j.LoggerFactory; // Added manual import
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
// REMOVED @Slf4j
public class PushNotificationService {

    // ADDED MANUAL LOGGER (This replaces @Slf4j)
    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);

    private final WebClient.Builder webClientBuilder;

    public PushNotificationService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Async("pushNotificationExecutor")
    public void sendPushNotification(String pushToken, String title, String body) {
        if (pushToken == null || pushToken.isBlank()) return;
        try {
            Map<String, Object> message = Map.of(
                    "to", pushToken,
                    "title", title,
                    "body", body,
                    "sound", "default"
            );

            webClientBuilder.build()
                    .post()
                    .uri("https://exp.host/--/api/v2/push/send")
                    .bodyValue(message)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                            r -> log.info("Push sent to {}", pushToken), // Now 'log' is recognized
                            e -> log.error("Push failed: {}", e.getMessage())
                    );
        } catch (Exception e) {
            log.error("Push notification error: {}", e.getMessage());
        }
    }
}