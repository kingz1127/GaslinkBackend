package com.gaslink.api.modules.notification;

import com.gaslink.api.modules.notification.dto.*;
import com.gaslink.api.modules.user.UserRepository;
import com.gaslink.api.modules.user.User;
import com.gaslink.api.shared.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final PushNotificationService pushNotificationService;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository,
                               PushNotificationService pushNotificationService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.pushNotificationService = pushNotificationService;
    }

    @Transactional
    public void notify(UUID userId, String type, String title, String body) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setType(type);
        n.setTitle(title);
        n.setBody(body);
        n.setRead(false);

        notificationRepository.save(n);

        userRepository.findById(userId).ifPresent(u -> {
            // NOTE: Ensure your User class has getPushToken() manually added!
            if (u.getPushToken() != null) {
                pushNotificationService.sendPushNotification(u.getPushToken(), title, body);
            }
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

        if (!n.getUserId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }

        n.setRead(true);
        notificationRepository.save(n);
    }

    @Transactional
    public void markAllRead(UUID userId) {
        // This assumes your repository has a custom method for this
        notificationRepository.markAllRead(userId);
    }

    private NotificationDto toDto(Notification n) {
        return new NotificationDto(
                n.getId(),
                n.getType(),
                n.getTitle(),
                n.getBody(),
                n.isRead(),
                n.getCreatedAt()
        );
    }
}