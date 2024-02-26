package rsupport.test.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MvcExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        ModelAndView mv =  new ModelAndView();
        mv.setViewName("/error");
        return mv;
    }

    // 실제로 예외를 처리할 메서드
    private void handleException(HttpServletResponse response, Exception exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        // 클라이언트에 보여질 json객체 만들기
        Map<String, Object> result = new HashMap<>();
        result.put("exception", exception.getClass());
        result.put("message", exception.getMessage());

        String result1 = objMapper.writeValueAsString(result); // Object -> String

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(result1);
    }
}
