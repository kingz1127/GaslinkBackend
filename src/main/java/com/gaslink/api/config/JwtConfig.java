//package com.gaslink.api.config;
//
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.crypto.SecretKey;
//import java.util.Base64;
//
//@Configuration
//public class JwtConfig {
//
//    @Value("${app.jwt.secret}")
//    private String jwtSecret;
//
//    public static final long ACCESS_TOKEN_EXPIRY_MS  = 15 * 60 * 1000L;        // 15 min
//    public static final long REFRESH_TOKEN_EXPIRY_MS = 7 * 24 * 60 * 60 * 1000L; // 7 days
//
//    @Bean
//    public SecretKey jwtSecretKey() {
//        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}

package com.gaslink.api.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JwtConfig {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public static final long ACCESS_TOKEN_EXPIRY_MS = 15 * 60 * 1000L;        // 15 minutes
    public static final long REFRESH_TOKEN_EXPIRY_MS = 7 * 24 * 60 * 60 * 1000L; // 7 days

    @Bean
    public SecretKey jwtSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}