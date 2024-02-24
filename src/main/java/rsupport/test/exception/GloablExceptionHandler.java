package rsupport.test.exception;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Map;

import static rsupport.test.support.Utils.errorResponse;
import static rsupport.test.support.Utils.extractErrorResult;

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


    @ExceptionHandler({
            MaxUploadSizeExceededException.class,
            FileSizeLimitExceededException.class
    })
    public ResponseEntity<Map<String, Object>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>(errorResponse.apply(HttpStatus.PAYLOAD_TOO_LARGE, "한 파일은 50MB이하. 전체 파일은 200MB까지 업로드가 가능합니다."), HttpStatus.PAYLOAD_TOO_LARGE);
    }



}
