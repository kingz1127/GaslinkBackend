package com.gaslink.api.modules.order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, UUID> {
    List<OrderStatusHistory> findByOrderIdOrderByCreatedAtAsc(UUID orderId);
}