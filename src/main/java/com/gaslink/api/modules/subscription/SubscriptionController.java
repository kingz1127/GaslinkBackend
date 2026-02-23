package com.gaslink.api.modules.subscription;
import com.gaslink.api.modules.subscription.dto.*;
import com.gaslink.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/v1/subscriptions") @RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<ApiResponse<SubscriptionDto>> getMySubscription(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success(subscriptionService.getMySubscription(UUID.fromString(auth.getName()))));
    }

    @PostMapping
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<ApiResponse<SubscriptionDto>> subscribe(Authentication auth, @Valid @RequestBody CreateSubscriptionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(subscriptionService.subscribe(UUID.fromString(auth.getName()), req)));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<SubscriptionDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(subscriptionService.getAll()));
    }
}