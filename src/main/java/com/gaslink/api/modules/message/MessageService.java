package com.gaslink.api.modules.message;
import com.gaslink.api.modules.message.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public MessageDto sendMessage(UUID senderId, SendMessageRequest req) {
        Message m = Message.builder().orderId(req.getOrderId()).senderId(senderId).content(req.getContent()).build();
        m = messageRepository.save(m);
        MessageDto dto = toDto(m);
        messagingTemplate.convertAndSend("/topic/order." + req.getOrderId(), dto);
        return dto;
    }

    public List<MessageDto> getHistory(UUID orderId) {
        return messageRepository.findByOrderIdOrderByCreatedAtAsc(orderId).stream().map(this::toDto).collect(Collectors.toList());
    }

    private MessageDto toDto(Message m) {
        return MessageDto.builder().id(m.getId()).orderId(m.getOrderId()).senderId(m.getSenderId())
                .content(m.getContent()).isRead(m.isRead()).createdAt(m.getCreatedAt()).build();
    }
}