package rsupport.test.domain.notice.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AttachmentQueryRepository {

    Long disableAll(Long noticeId, String updateId, LocalDateTime updateDate);

}
