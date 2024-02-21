package rsupport.test.domain.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rsupport.test.domain.storage.entity.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
