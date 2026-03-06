package com.gaslink.api.modules.payment;
import com.gaslink.api.modules.payment.dto.*;
import com.gaslink.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController @RequestMapping("/api/v1/payments")
//@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<ApiResponse<InitiatePaymentResponse>> initiate(@Valid @RequestBody InitiatePaymentRequest req) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.initiatePayment(req)));
    }

    @GetMapping("/verify/{reference}")
    public ResponseEntity<ApiResponse<Void>> verify(@PathVariable String reference) {
        paymentService.verifyPayment(reference);
        return ResponseEntity.ok(ApiResponse.success("Payment verified", null));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody String payload,
            @RequestHeader("x-paystack-signature") String signature) {
        paymentService.handleWebhook(payload, signature);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/confirm-cash")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<ApiResponse<Void>> confirmCash(@PathVariable UUID orderId) {
        paymentService.confirmCashPayment(orderId);
        return ResponseEntity.ok(ApiResponse.success("Cash payment confirmed", null));
    }
}