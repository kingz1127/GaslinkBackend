package com.gaslink.api.modules.subscription;
import com.gaslink.api.modules.subscription.dto.*;
import com.gaslink.api.shared.enums.SubscriptionStatus;
import com.gaslink.api.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional
    public SubscriptionDto subscribe(UUID vendorId, CreateSubscriptionRequest req) {
        BigDecimal amount = "MONTHLY".equalsIgnoreCase(req.getBillingCycle()) ? new BigDecimal("5000") : new BigDecimal("50000");
        Instant expiry = "MONTHLY".equalsIgnoreCase(req.getBillingCycle()) ?
                Instant.now().plus(30, java.time.temporal.ChronoUnit.DAYS) :
                Instant.now().plus(365, java.time.temporal.ChronoUnit.DAYS);
        Subscription s = Subscription.builder().vendorId(vendorId).plan(req.getPlan())
                .amount(amount).billingCycle(req.getBillingCycle())
                .startedAt(Instant.now()).expiresAt(expiry).build();
        return toDto(subscriptionRepository.save(s));
    }

    public SubscriptionDto getMySubscription(UUID vendorId) {
        return subscriptionRepository.findTopByVendorIdOrderByExpiresAtDesc(vendorId)
                .map(this::toDto).orElseThrow(() -> new ResourceNotFoundException("No subscription found"));
    }

    public List<SubscriptionDto> getAll() {
        return subscriptionRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void expireOldSubscriptions() {
        subscriptionRepository.findByStatusAndExpiresAtBefore(SubscriptionStatus.ACTIVE, Instant.now()).forEach(s -> {
            s.setStatus(SubscriptionStatus.EXPIRED);
            subscriptionRepository.save(s);
        });
    }

    private SubscriptionDto toDto(Subscription s) {
        return SubscriptionDto.builder().id(s.getId()).vendorId(s.getVendorId()).plan(s.getPlan())
                .amount(s.getAmount()).billingCycle(s.getBillingCycle()).status(s.getStatus())
                .startedAt(s.getStartedAt()).expiresAt(s.getExpiresAt()).build();
    }
}