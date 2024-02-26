package rsupport.test.config.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
//@RequiredArgsConstructor
public class HandleAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    private final ObjectMapper objectMapper;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    // TODO. resolver 처리
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // resolver not working. after spring 6.2 will deprecate.
        resolver.resolveException(request, response, null, exception);
//        String reson = Optional.ofNullable(request.getAttribute("message"))
//                .map(o -> (String)o).orElse(exception.getMessage());
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        response.getWriter().write(objectMapper.writeValueAsString(errorResponse.apply(HttpStatus.UNAUTHORIZED, reson)));
    }
}
