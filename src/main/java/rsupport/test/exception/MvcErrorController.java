package rsupport.test.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Hidden
@RestController
public class MvcErrorController extends AbstractErrorController {

    public MvcErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    // replace spring mvc error page .
    @RequestMapping(value = "${server.error.path:${error.path:/error}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> customError(HttpServletRequest request) {
        HttpStatus httpStatus = getStatus(request);
        Map<String, Object> errorAttribute =
                getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.BINDING_ERRORS, ErrorAttributeOptions.Include.MESSAGE));
        return new ResponseEntity<>(errorAttribute, httpStatus);
    }

}