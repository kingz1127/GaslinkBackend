package com.gaslink.api.modules.auth;
import com.gaslink.api.config.JwtConfig;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final SecretKey jwtSecretKey;

    public String generateAccessToken(UUID userId, String role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JwtConfig.ACCESS_TOKEN_EXPIRY_MS))
                .signWith(jwtSecretKey)
                .compact();
    }

    public String generateRefreshToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JwtConfig.REFRESH_TOKEN_EXPIRY_MS))
                .signWith(jwtSecretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try { parseClaims(token); return true; }
        catch (JwtException | IllegalArgumentException e) { return false; }
    }

    public String extractUserId(String token) { return parseClaims(token).getSubject(); }
    public String extractRole(String token) { return parseClaims(token).get("role", String.class); }

    private Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token).getPayload();
    }
}