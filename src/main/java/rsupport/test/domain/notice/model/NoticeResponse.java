package rsupport.test.domain.notice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class NoticeResponse {
    private Notice notice;
}
