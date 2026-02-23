package com.gaslink.api.modules.message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByOrderIdOrderByCreatedAtAsc(UUID orderId);
}