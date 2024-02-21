package rsupport.test.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.Notice;
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
