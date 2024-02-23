package rsupport.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
public class GloablExceptionHandler {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        HttpStatus status = ex.getErrorCode().getStatus();
        return new ResponseEntity<>(errorResponse.apply(status, ex.getErrorMessage()), status);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(errorResponse.apply(HttpStatus.BAD_REQUEST, extractErrorResult.apply(ex.getBindingResult())));
    }

    /*
     * Extract binding result
     */
    private final Function<BindingResult, Map<String, String>> extractErrorResult = (result) -> result.getAllErrors().stream()
            .collect(Collectors.toMap(
                    k -> ((FieldError)k).getField(),
                    v -> Optional.ofNullable(v.getDefaultMessage()).orElse(""))
            );


    /*
     * Make error response
     */
    private final BiFunction<HttpStatus, Object, Map<String, Object>> errorResponse = (status, message) -> {
        Map<String, Object> errorAttribute = new LinkedHashMap<>();
        errorAttribute.put("timestamp", LocalDateTime.now());
        errorAttribute.put("status", status.value());
        errorAttribute.put("message", message);
        return errorAttribute;
    };


}
