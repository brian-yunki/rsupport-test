package rsupport.test.domain.notice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.model.NoticeSearch;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeQueryRepository {

    List<NoticeEntity> searchBy(NoticeSearch noticeSearch, Pageable pageable);

    Long searchByCount(NoticeSearch noticeSearch);

    Optional<NoticeEntity> selectById(Long id);

    Long updateCount(Long id);

    Long count(Long id);

}
