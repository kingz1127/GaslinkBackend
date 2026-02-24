package com.gaslink.api.modules.payment;

import com.gaslink.api.modules.order.*;
import com.gaslink.api.modules.payment.dto.*;
import com.gaslink.api.shared.enums.*;
import com.gaslink.api.shared.exception.*;
import com.gaslink.api.shared.util.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaystackClient paystackClient;

    @Value("${app.paystack.webhook-secret}")
    private String webhookSecret;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository,
                          PaystackClient paystackClient) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.paystackClient = paystackClient;
    }

    @Transactional
    public InitiatePaymentResponse initiatePayment(InitiatePaymentRequest req) {
        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            throw new BusinessRuleException("Order already paid");
        }

        InitiatePaymentResponse resp = paystackClient.initializeTransaction(
                req.getEmail(), order.getTotal(), order.getReference(), req.getCallbackUrl());

        Payment p = Payment.builder()
                .orderId(order.getId())
                .gateway("paystack")
                .gatewayRef(resp.getReference())
                .amount(order.getTotal())
                .currency("NGN")
                .build();

        paymentRepository.save(p);
        return resp;
    }

    @Transactional
    public void verifyPayment(String reference) {
        boolean ok = paystackClient.verifyTransaction(reference);
        Payment p = paymentRepository.findByGatewayRef(reference)
                .orElseThrow(() -> new ResourceNotFoundException("Payment record not found"));

        if (ok) {
            p.setStatus(PaymentStatus.PAID);
            p.setPaidAt(Instant.now());
            paymentRepository.save(p);

            Order o = orderRepository.findById(p.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
            o.setPaymentStatus(PaymentStatus.PAID);
            orderRepository.save(o);
        }
    }

    @Transactional
    public void handleWebhook(String payload, String signature) {
        if (!HmacUtils.verifySignature(payload, webhookSecret, signature)) {
            throw new PaymentVerificationException("Invalid webhook signature");
        }
        // event parsing would happen here with ObjectMapper
    }

    @Transactional
    public void confirmCashPayment(UUID orderId) {
        Order o = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (o.getPaymentMethod() != PaymentMethod.PAY_ON_DELIVERY) {
            throw new BusinessRuleException("Order is not pay-on-delivery");
        }

        o.setPaymentStatus(PaymentStatus.PAID);
        orderRepository.save(o);

        paymentRepository.findByOrderId(orderId).ifPresent(p -> {
            p.setStatus(PaymentStatus.PAID);
            p.setPaidAt(Instant.now());
            paymentRepository.save(p);
        });
    }
}