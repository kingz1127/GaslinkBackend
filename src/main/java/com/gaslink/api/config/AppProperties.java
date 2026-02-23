package com.gaslink.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Jwt jwt = new Jwt();
    private S3 s3 = new S3();
    private Otp otp = new Otp();

    @Data public static class Jwt { private String secret; }
    @Data public static class S3 { private String endpoint; private String accessKey; private String secretKey; private String region; private String bucket; }
    @Data public static class Otp { private int expiryMinutes = 5; }
}
