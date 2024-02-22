package rsupport.test.domain.notice.repository;

import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.NoticeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeQueryRepository {

    List<NoticeEntity> selectAll();

    Optional<NoticeEntity> selectById(Long id);

    Long updateCount(Long id);

}
