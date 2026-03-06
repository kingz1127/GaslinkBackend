package com.gaslink.api.modules.auth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(UUID userId);
}