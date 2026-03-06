package com.gaslink.api.modules.order;
import com.gaslink.api.shared.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.*;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);
    List<Order> findByVendorIdOrderByCreatedAtDesc(UUID vendorId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByStatusAndCreatedAtBefore(OrderStatus status, Instant before);
}