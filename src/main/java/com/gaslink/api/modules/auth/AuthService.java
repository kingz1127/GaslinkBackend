package com.gaslink.api.modules.auth;

import com.gaslink.api.config.JwtConfig;
import com.gaslink.api.modules.auth.dto.*;
import com.gaslink.api.modules.user.*;
import com.gaslink.api.modules.user.dto.UserProfileDto;
import com.gaslink.api.shared.enums.UserRole;
import com.gaslink.api.shared.exception.*;
import jakarta.validation.Valid;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder,
                       RedisTemplate<String, String> redisTemplate) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepository.existsByPhone(req.getPhone())) throw new BusinessRuleException("Phone already registered");
        if (req.getEmail() != null && userRepository.existsByEmail(req.getEmail())) throw new BusinessRuleException("Email already registered");

        User user = new User();
        user.setFullName(req.getFullName());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user.setActive(false);

        userRepository.save(user);
        sendOtp(req.getPhone());
    }

    public void sendOtp(String phone) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        redisTemplate.opsForValue().set("otp:" + phone, otp, 5, TimeUnit.MINUTES);
        // Dev log
        System.out.println("OTP for " + phone + " => " + otp);
    }

    @Transactional
    public void verifyOtp(@Valid OtpVerifyRequest req) {
        String stored = redisTemplate.opsForValue().get("otp:" + req.getPhone());
        if (stored == null || !stored.equals(req.getOtpCode())) {
            throw new BusinessRuleException("Invalid or expired OTP");
        }

        User user = userRepository.findByPhone(req.getPhone())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setActive(true);
        userRepository.save(user);
        redisTemplate.delete("otp:" + req.getPhone());
    }

    @Transactional
    public AuthResponse login(@Valid LoginRequest req) {
        User user = userRepository.findByPhoneOrEmail(req.getPhoneOrEmail(), req.getPhoneOrEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        if (!user.isActive()) {
            throw new ForbiddenException("Account not verified. Please verify your OTP.");
        }

        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse refreshToken(@Valid RefreshTokenRequest req) {
        RefreshToken rt = refreshTokenRepository.findByToken(req.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now())) {
            throw new UnauthorizedException("Refresh token expired");
        }

        rt.setRevoked(true);
        refreshTokenRepository.save(rt);

        User user = userRepository.findById(rt.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return buildAuthResponse(user);
    }

    @Transactional
    public void logout(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        RefreshToken rt = new RefreshToken();
        rt.setUserId(user.getId());
        rt.setToken(refreshToken);
        rt.setExpiresAt(Instant.now().plusMillis(JwtConfig.REFRESH_TOKEN_EXPIRY_MS));
        rt.setRevoked(false);
        refreshTokenRepository.save(rt);

        UserProfileDto profile = new UserProfileDto(
                user.getId(),
                user.getFullName(),
                user.getPhone(),
                user.getEmail(),
                user.getRole(),
                user.getAvatarUrl()
        );

        return new AuthResponse(
                accessToken,
                refreshToken,
                JwtConfig.ACCESS_TOKEN_EXPIRY_MS / 1000,
                profile
        );
    }
}