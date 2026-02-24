package com.gaslink.api.modules.order;
import com.gaslink.api.modules.order.dto.*;
import com.gaslink.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/v1/orders")
//@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<OrderDto>> create(Authentication auth, @Valid @RequestBody CreateOrderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Order placed", orderService.createOrder(UUID.fromString(auth.getName()), req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrder(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrder(id)));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<OrderSummaryDto>>> myOrders(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getCustomerOrders(UUID.fromString(auth.getName()))));
    }

    @GetMapping("/vendor")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<ApiResponse<List<OrderSummaryDto>>> vendorOrders(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getVendorOrders(UUID.fromString(auth.getName()))));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('VENDOR','ADMIN')")
    public ResponseEntity<ApiResponse<OrderDto>> updateStatus(Authentication auth, @PathVariable UUID id, @Valid @RequestBody UpdateOrderStatusRequest req) {
        return ResponseEntity.ok(ApiResponse.success(orderService.updateStatus(id, UUID.fromString(auth.getName()), req)));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<OrderDto>> cancel(Authentication auth, @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(orderService.cancelOrder(id, UUID.fromString(auth.getName()))));
    }
}