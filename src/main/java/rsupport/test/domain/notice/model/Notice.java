package rsupport.test.domain.notice.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@Schema(name = "Notice", description = "공지사항")
public class Notice {

    @Schema(name = "id", description = "공지사항 아이디")
    private Long id;

    @NotBlank(message = "제목은 필수 입니다")
    @Size(max = 50, message = "제목은 50글자 이하로 입력해주세요")
    @Schema(name = "title", description = "공지제목")
    private String title;

    @NotBlank(message = "공지글은 필수 입니다")
    @Schema(name = "subject", description = "공지글")
    private String subject;

    @Schema(name = "startDate", description = "공지시작일")
    private LocalDateTime startDate;

    @Schema(name = "endDate", description = "공지종료일")
    private LocalDateTime endDate;

    @Schema(name = "viewCount", description = "조회수")
    private Long viewCount;

    @Schema(name = "useYn", description = "사용유무")
    private String useYn;

    @Builder.Default
    @Schema(name = "attachments", description = "첨부파일")
    private List<Attachment> attachments = new ArrayList<>();

    @Schema(name = "createId", description = "등록자아이디")
    private String createId;

    @Schema(name = "createDate", description = "등록일")
    private LocalDateTime createDate;

    @Schema(name = "updateId", description = "수정자아이디")
    private String updateId;

    @Schema(name = "updateDate", description = "수정일")
    private LocalDateTime updateDate;
}
