package com.gaslink.api.modules.notification;
import com.gaslink.api.modules.notification.dto.*;
import com.gaslink.api.modules.user.UserRepository;
import com.gaslink.api.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PushNotificationService pushNotificationService;

    @Transactional
    public void notify(UUID userId, String type, String title, String body) {
        Notification n = Notification.builder().userId(userId).type(type).title(title).body(body).build();
        notificationRepository.save(n);
        userRepository.findById(userId).ifPresent(u -> {
            if (u.getPushToken() != null) pushNotificationService.sendPushNotification(u.getPushToken(), title, body);
        });
    }

    public List<NotificationDto> getNotifications(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void markRead(UUID userId, UUID notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
        if (!n.getUserId().equals(userId)) throw new ForbiddenException("Access denied");
        n.setRead(true);
        notificationRepository.save(n);
    }

    @Transactional
    public void markAllRead(UUID userId) { notificationRepository.markAllRead(userId); }

    private NotificationDto toDto(Notification n) {
        return NotificationDto.builder().id(n.getId()).type(n.getType()).title(n.getTitle())
                .body(n.getBody()).isRead(n.isRead()).createdAt(n.getCreatedAt()).build();
    }
}