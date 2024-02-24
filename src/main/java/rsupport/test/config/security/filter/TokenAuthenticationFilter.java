package rsupport.test.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rsupport.test.config.security.token.TokenService;

import java.io.IOException;
import java.util.List;

import static rsupport.test.support.Utils.parseBearerToken;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = parseBearerToken.apply(request.getHeader(HttpHeaders.AUTHORIZATION)).orElse("");
            tokenService.validateToken(token);
            Authentication authentication = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    // exclude
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<String> exclude = List.of("/login");
        String path = request.getRequestURI();
        return (request.getMethod().equals("GET") || exclude.stream().anyMatch(path::startsWith));
    }

}
