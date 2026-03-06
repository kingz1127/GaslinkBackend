package com.gaslink.api.scheduler;

import com.gaslink.api.modules.subscription.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionExpiryScheduler {
    private static final Logger log = LoggerFactory.getLogger(SubscriptionExpiryScheduler.class);

    private final SubscriptionService subscriptionService;

    public SubscriptionExpiryScheduler(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Scheduled(cron = "0 0 0 * * *") // daily at midnight
    public void expireSubscriptions() {
        subscriptionService.expireOldSubscriptions();
        log.info("Subscription expiry check completed");
    }
}