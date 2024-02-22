package rsupport.test.domain.notice.repository;

import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.Notice;

import java.util.List;

@Repository
public interface NoticeQueryRepository {

    List<Notice> findAllByUseYnEqualsIsTrue();

    Long updateCount(Long id);

}
