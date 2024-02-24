package rsupport.test.exception;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
public class GloablExceptionHandler {

    private static final String ERROR_KEY_TIMESTAMP = "timestamp";
    private static final String ERROR_KEY_STATUS = "status";
    private static final String ERROR_KEY_MESSAGE = "message";

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<Map<String, Object>> handleCustomException(CustomException ex) {
        HttpStatus status = ex.getErrorCode().getStatus();
        return new ResponseEntity<>(errorResponse.apply(status, ex.getErrorMessage()), status);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(errorResponse.apply(HttpStatus.BAD_REQUEST, extractErrorResult.apply(ex.getBindingResult())));
    }


    @ExceptionHandler({
            MaxUploadSizeExceededException.class,
            FileSizeLimitExceededException.class
    })
    public ResponseEntity<Map<String, Object>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(errorResponse.apply(HttpStatus.PAYLOAD_TOO_LARGE, "한 파일은 50MB이하. 전체 파일은 200MB까지 업로드가 가능합니다."), HttpStatus.PAYLOAD_TOO_LARGE);
    }

    /*
     * Extract binding result
     */
    private final Function<BindingResult, Map<String, String>> extractErrorResult = (result) -> result.getAllErrors().stream()
            .collect(Collectors.toMap(
                    k -> ((FieldError)k).getField(),
                    v -> Optional.ofNullable(v.getDefaultMessage()).orElse(""))
            );


    /*전체
     * Make error response
     */
    private final BiFunction<HttpStatus, Object, Map<String, Object>> errorResponse = (status, message) -> {
        Map<String, Object> errorAttribute = new LinkedHashMap<>();
        errorAttribute.put(ERROR_KEY_TIMESTAMP, LocalDateTime.now());
        errorAttribute.put(ERROR_KEY_STATUS, status.value());
        errorAttribute.put(ERROR_KEY_MESSAGE, message);
        return errorAttribute;
    };


}
