package com.gaslink.api.scheduler;
import com.gaslink.api.modules.order.*;
import com.gaslink.api.shared.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
//@RequiredArgsConstructor @Slf4j
public class OrderTimeoutScheduler {
    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;

    public OrderTimeoutScheduler(OrderRepository orderRepository, OrderStatusHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelTimedOutOrders() {
        Instant cutoff = Instant.now().minus(5, ChronoUnit.MINUTES);
        List<Order> stale = orderRepository.findByStatusAndCreatedAtBefore(OrderStatus.PENDING, cutoff);
        stale.forEach(o -> {
            o.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(o);
            historyRepository.save(OrderStatusHistory.builder()
                    .orderId(o.getId()).status(OrderStatus.CANCELLED)
                    .note("Auto-cancelled: no vendor accepted within 5 minutes").build());
            log.info("Auto-cancelled order {}", o.getReference());
        });
    }
}