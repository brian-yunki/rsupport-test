package rsupport.test.config.security.token;

import lombok.Builder;

@Builder
public record Token(
        String grantType,
        String accessToken,
        Long accessTokenExpiresIn,
        String refreshToken,
        Long refreshTokenExpiresIn
//        String scope
) { }
