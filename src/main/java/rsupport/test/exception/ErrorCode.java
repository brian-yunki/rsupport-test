package rsupport.test.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에러가 발생했습니다"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못 된 요청입니다"),
    NOT_FOUNT(HttpStatus.NOT_FOUND, "항목을 찾을 수 없습니다"),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
