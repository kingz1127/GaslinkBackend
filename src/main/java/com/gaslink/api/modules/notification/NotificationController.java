package com.gaslink.api.modules.notification;
import com.gaslink.api.modules.notification.dto.*;
import com.gaslink.api.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/v1/notifications")
//@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationDto>>> getAll(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.getNotifications(UUID.fromString(auth.getName()))));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markRead(Authentication auth, @PathVariable UUID id) {
        notificationService.markRead(UUID.fromString(auth.getName()), id);
        return ResponseEntity.ok(ApiResponse.success("Marked as read", null));
    }

    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllRead(Authentication auth) {
        notificationService.markAllRead(UUID.fromString(auth.getName()));
        return ResponseEntity.ok(ApiResponse.success("All marked as read", null));
    }
}