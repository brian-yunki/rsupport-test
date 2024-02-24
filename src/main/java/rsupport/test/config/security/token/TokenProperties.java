package rsupport.test.config.security.token;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record TokenProperties(
        String secret,
        long accessTokenExpiresIn,
        long refreshTokenExpiresIn
) { }
