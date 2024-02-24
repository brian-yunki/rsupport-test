package rsupport.test.support;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

    private static final String ERROR_KEY_TIMESTAMP = "timestamp";
    private static final String ERROR_KEY_STATUS = "status";
    private static final String ERROR_KEY_MESSAGE = "message";

    // Runtime Processor수에 따라 ForkJoinPool 사이즈 계산
    // pool size = (total processor size / argv (2)) + 1
    public static final Function<Integer, Integer> calculatedPoolSize = (argv) -> (Runtime.getRuntime().availableProcessors()/argv) + 1;


    // parse bearer token
    public static final Function<String, Optional<String>> parseBearerToken = (token) -> {
        if (null == token) return Optional.empty();
        Matcher matcher = Pattern.compile("^Bearer *([^ ]+) *$", Pattern.CASE_INSENSITIVE).matcher(token);
        try {
            if (matcher.matches()) {
                return Optional.ofNullable(matcher.group(1));
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    };


    // extract vaildation binding result
    public static final Function<BindingResult, Map<String, String>> extractErrorResult = (result) -> result.getAllErrors().stream()
            .collect(Collectors.toMap(
                    k -> ((FieldError)k).getField(),
                    v -> Optional.ofNullable(v.getDefaultMessage()).orElse(""))
            );


    // make default error message
    public static final BiFunction<HttpStatus, Object, Map<String, Object>> errorResponse = (status, message) -> {
        Map<String, Object> errorAttribute = new LinkedHashMap<>();
        errorAttribute.put(ERROR_KEY_TIMESTAMP, LocalDateTime.now());
        errorAttribute.put(ERROR_KEY_STATUS, status.value());
        errorAttribute.put(ERROR_KEY_MESSAGE, message);
        return errorAttribute;
    };
}
