package rsupport.test.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.test.storage.entity.FileEntity;
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
