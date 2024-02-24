package rsupport.test.domain.notice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@Schema(name = "Upload", description = "업로드 파일 ")
public class Upload {

    @Schema(name = "name", description = "파일이름")
    private String name;

    @Schema(name = "path", description = "파일경로")
    private String path;

    @Schema(name = "size", description = "파일사이즈")
    private Long size;

}
