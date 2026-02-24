package com.gaslink.api.modules.message;
import com.gaslink.api.modules.message.dto.*;
import com.gaslink.api.shared.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/v1/messages")
//@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<List<MessageDto>>> getHistory(@PathVariable UUID orderId) {
        return ResponseEntity.ok(ApiResponse.success(messageService.getHistory(orderId)));
    }
}