package rsupport.test.domain.notice.repository;

import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.NoticeEntity;

import java.util.List;

@Repository
public interface NoticeQueryRepository {

    List<NoticeEntity> findAllByUseYnEqualsIsTrue();
}
