package com.gaslink.api.modules.user;
import com.gaslink.api.modules.user.dto.*;
import com.gaslink.api.shared.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
//@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> getMe(Authentication auth) {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(UUID.fromString(auth.getName()))));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileDto>> updateMe(Authentication auth, @RequestBody UpdateProfileRequest req) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateProfile(UUID.fromString(auth.getName()), req)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Void>> setStatus(@PathVariable UUID id, @RequestParam boolean active) {
        userService.setUserStatus(id, active);
        return ResponseEntity.ok(ApiResponse.success("User status updated.", null));
    }
}