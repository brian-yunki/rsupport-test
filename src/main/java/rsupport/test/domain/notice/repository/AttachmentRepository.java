package rsupport.test.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>, AttachmentQueryRepository {
}
