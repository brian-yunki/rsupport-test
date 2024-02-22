package rsupport.test.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.NoticeFileEntity;

@Repository
public interface NoticeFileRepository extends JpaRepository<NoticeFileEntity, Long> {
}
