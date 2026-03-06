package com.gaslink.api.modules.message;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;  // <--- MAKE SURE THIS IS java.util.List
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    // Ensure the return type is List<Message>
    List<Message> findByOrderIdOrderBySentAtAsc(UUID orderId);
}