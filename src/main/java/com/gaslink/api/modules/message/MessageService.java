package com.gaslink.api.modules.message;

import com.gaslink.api.modules.message.dto.MessageDto;
import com.gaslink.api.modules.message.dto.SendMessageRequest;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDto> getHistory(UUID orderId) {
        // Explicitly get the list from the repository
        List<Message> messages = messageRepository.findByOrderIdOrderBySentAtAsc(orderId);

        // Convert the list of entities to list of DTOs
        return messages.stream()
                .map(m -> new MessageDto(
                        m.getId(),
                        m.getSenderId(),
                        m.getContent(),
                        m.getSentAt()
                ))
                .collect(Collectors.toList());
    }

    public MessageDto sendMessage(UUID senderId, SendMessageRequest req) {
        Message msg = new Message();
        msg.setOrderId(req.getOrderId());
        msg.setSenderId(senderId);
        msg.setContent(req.getContent());
        msg.setSentAt(Instant.now());

        Message saved = messageRepository.save(msg);
        return new MessageDto(
                saved.getId(),
                saved.getSenderId(),
                saved.getContent(),
                saved.getSentAt()
        );
    }
}