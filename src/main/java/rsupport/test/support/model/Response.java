package rsupport.test.support.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T content;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pageable pageable;

    private String message;

}
