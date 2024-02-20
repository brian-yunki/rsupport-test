package rsupport.test.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.test.notice.entity.NoticeEntity;
@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
}
