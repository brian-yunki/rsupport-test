package rsupport.test.domain.notice.repository;

import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.AttachmentEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentQueryRepository {

    Optional<AttachmentEntity> selectById(Long id);

    List<AttachmentEntity> selectByNoticeId(Long id);

    void disableAllByNoticeId(Long noticeId, String updateId, LocalDateTime updateDate);
}
