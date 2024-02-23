package rsupport.test.domain.notice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class Attachment {

    private Long id;
    @NotBlank(message = "파일이름은 필수 입니다")
    @Schema(name = "name", description = "파일이름")
    private String name;

    @NotBlank(message = "파일경로는 필수 입니다")
    @Schema(name = "path", description = "파일경로")
    private String path;

    @NotBlank(message = "파일사이즈는 필수 입니다")
    @Schema(name = "size", description = "파일사이즈")
    private Long size;

    @Schema(name = "useYn", description = "사용유무")
    private String useYn;

    @Schema(name = "createId", description = "등록자아이디")
    private String createId;

    @Schema(name = "createDate", description = "등록일")
    private LocalDateTime createDate;

    @Schema(name = "updateId", description = "수정자아이디")
    private String updateId;

    @Schema(name = "updateDate", description = "수정일")
    private LocalDateTime updateDate;
}
