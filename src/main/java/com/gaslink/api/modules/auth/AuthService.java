package com.gaslink.api.modules.auth;
import com.gaslink.api.config.JwtConfig;
import com.gaslink.api.modules.auth.dto.*;
import com.gaslink.api.modules.user.*;
import com.gaslink.api.modules.user.dto.UserProfileDto;
import com.gaslink.api.shared.enums.UserRole;
import com.gaslink.api.shared.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepository.existsByPhone(req.getPhone())) throw new BusinessRuleException("Phone already registered");
        if (req.getEmail() != null && userRepository.existsByEmail(req.getEmail())) throw new BusinessRuleException("Email already registered");

        User user = User.builder()
                .fullName(req.getFullName()).phone(req.getPhone()).email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(UserRole.CUSTOMER).isActive(false).build();
        userRepository.save(user);
        sendOtp(req.getPhone());
    }

    public void sendOtp(String phone) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        redisTemplate.opsForValue().set("otp:" + phone, otp, 5, TimeUnit.MINUTES);
        // TODO: integrate Termii SMS gateway here
        System.out.println("OTP for " + phone + " => " + otp); // dev only
    }

    @Transactional
    public void verifyOtp(OtpVerifyRequest req) {
        String stored = redisTemplate.opsForValue().get("otp:" + req.getPhone());
        if (stored == null || !stored.equals(req.getOtpCode())) throw new BusinessRuleException("Invalid or expired OTP");
        User user = userRepository.findByPhone(req.getPhone()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(true);
        userRepository.save(user);
        redisTemplate.delete("otp:" + req.getPhone());
    }

    @Transactional
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByPhoneOrEmail(req.getPhoneOrEmail(), req.getPhoneOrEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) throw new UnauthorizedException("Invalid credentials");
        if (!user.isActive()) throw new ForbiddenException("Account not verified. Please verify your OTP.");
        return buildAuthResponse(user);
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest req) {
        RefreshToken rt = refreshTokenRepository.findByToken(req.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));
        if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now())) throw new UnauthorizedException("Refresh token expired");
        rt.setRevoked(true);
        refreshTokenRepository.save(rt);
        User user = userRepository.findById(rt.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return buildAuthResponse(user);
    }

    @Transactional
    public void logout(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    private AuthResponse buildAuthResponse(User user) {
        String accessToken  = jwtTokenProvider.generateAccessToken(user.getId(), user.getRole().name());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        RefreshToken rt = RefreshToken.builder()
                .userId(user.getId()).token(refreshToken)
                .expiresAt(Instant.now().plusMillis(JwtConfig.REFRESH_TOKEN_EXPIRY_MS)).build();
        refreshTokenRepository.save(rt);
        UserProfileDto profile = UserProfileDto.builder()
                .id(user.getId()).fullName(user.getFullName()).phone(user.getPhone())
                .email(user.getEmail()).role(user.getRole()).avatarUrl(user.getAvatarUrl()).build();
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken)
                .expiresIn(JwtConfig.ACCESS_TOKEN_EXPIRY_MS / 1000).user(profile).build();
    }
}