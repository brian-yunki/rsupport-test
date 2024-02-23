package rsupport.test.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T content;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private org.springframework.data.domain.Pageable pageable;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Long totalElements;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Integer totalPages;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Boolean last;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Integer numberOfElements;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Integer number;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Integer size;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private Boolean first;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Pageable pageable;


    private String message;

}
