package com.gaslink.api.modules.subscription;
import com.gaslink.api.shared.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.*;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findTopByVendorIdOrderByExpiresAtDesc(UUID vendorId);
    List<Subscription> findByStatusAndExpiresAtBefore(SubscriptionStatus status, Instant now);
}