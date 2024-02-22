package rsupport.test.domain.notice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
public class Notice {

    private Long id;
    private String title;
    private String subject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long viewCount;
    private String useYn;

    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    private String createId;
    private LocalDateTime createDate;
    private String updateId;
    private LocalDateTime updateDate;
}
