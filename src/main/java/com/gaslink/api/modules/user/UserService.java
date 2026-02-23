package com.gaslink.api.modules.user;
import com.gaslink.api.modules.user.dto.*;
import com.gaslink.api.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserProfileDto getProfile(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toDto(user);
    }

    @Transactional
    public UserProfileDto updateProfile(UUID userId, UpdateProfileRequest req) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (req.getFullName() != null) user.setFullName(req.getFullName());
        if (req.getAvatarUrl() != null) user.setAvatarUrl(req.getAvatarUrl());
        if (req.getPushToken() != null) user.setPushToken(req.getPushToken());
        return toDto(userRepository.save(user));
    }

    @Transactional
    public void setUserStatus(UUID userId, boolean active) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(active);
        userRepository.save(user);
    }

    private UserProfileDto toDto(User u) {
        return UserProfileDto.builder().id(u.getId()).fullName(u.getFullName())
                .phone(u.getPhone()).email(u.getEmail()).role(u.getRole()).avatarUrl(u.getAvatarUrl()).build();
    }
}