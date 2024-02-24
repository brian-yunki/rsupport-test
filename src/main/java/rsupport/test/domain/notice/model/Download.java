package rsupport.test.domain.notice.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Hidden
@Builder
@Setter @Getter
public class Download {

    private Resource resource;
    private String name;
    private String contentType;
}
