package rsupport.test.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.test.notice.entity.Notice;
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
