package rsupport.test.support.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pageable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageSize;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPages;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long totalElements;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean first;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean last;
}
