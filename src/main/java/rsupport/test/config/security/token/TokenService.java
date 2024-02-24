package rsupport.test.config.security.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rsupport.test.config.security.SecurityUserDetails;

import javax.crypto.SecretKey;
import javax.security.sasl.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@EnableConfigurationProperties(TokenProperties.class)
public class TokenService {
    private static final String GRANT_TYPE_KEY = "Bearer";
    private static final String CLAIMS_KEY_AUTHORITIES = "roles";
    private static final String CLAIMS_KEY_USER_ID = "userid";
    private static final String HEADER_KEY_TYPE = "JWT";

    private final TokenProperties tokenProperties;
    private final SecretKey secretKey;

    public TokenService(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
        byte[] keyBytes = tokenProperties.secret().getBytes();
        this.secretKey  = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(keyBytes)
                .getBytes(StandardCharsets.UTF_8));
    }

    public Token generateLoginToken(Authentication authentication) {
        String username    = authentication.getName(); // subject == loginId
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Instant issuedAt  = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiresIn = issuedAt.plus(tokenProperties.accessTokenExpiresIn(), ChronoUnit.MILLIS);
        Instant refreshExpiresIn = issuedAt.plus(tokenProperties.refreshTokenExpiresIn(), ChronoUnit.MILLIS);

        return Token.builder()
                .grantType(TokenService.GRANT_TYPE_KEY)
                .accessToken(createToken(username, authorities, Date.from(issuedAt), Date.from(expiresIn)))
                .accessTokenExpiresIn(Date.from(expiresIn).getTime() / 1000)
                .refreshToken(createToken(username, authorities, Date.from(issuedAt), Date.from(refreshExpiresIn)))
                .refreshTokenExpiresIn(Date.from(refreshExpiresIn).getTime() / 1000)
                .build();
    }

    private String createToken(String subject, String authorities, Date issuedAt, Date tokenExpireIn) {
        return Jwts.builder()
                .header()
                    .type(TokenService.HEADER_KEY_TYPE)
                    .and()
                .claims()
                    .subject(subject)
                    .issuedAt(issuedAt)
                    .expiration(tokenExpireIn)
                    .id(UUID.randomUUID().toString())
                    .add(TokenService.CLAIMS_KEY_AUTHORITIES, authorities)
                    .add(TokenService.CLAIMS_KEY_USER_ID, subject)
                .and()
                    .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public void validateToken(String token) throws AuthenticationException {
        try {
            Jwts.parser()
                    .verifyWith(this.secretKey).build()
                    .parseSignedClaims(token).getPayload();
//            log.info("expiration date: {}", claims.getPayload().getExpiration());
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("토큰이 만료되었습니다");
        } catch (UnsupportedJwtException e) {
            throw new AuthenticationException("지원되지 않는 JWT 토큰입니다");
        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("JWT 토큰이 잘못되었습니다");
        } catch (SecurityException e) {
            throw new AuthenticationException("잘못된 JWT 서명입니다");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload();

        String authority = Optional.ofNullable(claims.get(TokenService.CLAIMS_KEY_AUTHORITIES))
                .map(Object::toString)
                .orElse("");

        SecurityUserDetails principal = SecurityUserDetails.builder()
                .username(claims.getSubject())
                .password("")
                .authority(authority)
                .useYn(true)
                .build();

        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

}
