package com.gaslink.api.scheduler;
import com.gaslink.api.modules.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor @Slf4j
public class SubscriptionExpiryScheduler {
    private final SubscriptionService subscriptionService;

    @Scheduled(cron = "0 0 0 * * *") // daily at midnight
    public void expireSubscriptions() {
        subscriptionService.expireOldSubscriptions();
        log.info("Subscription expiry check completed");
    }
}