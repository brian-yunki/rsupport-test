package rsupport.test.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeQueryRepository {

    @Modifying
    @Query("update Notice notice set notice.useYn = :useYn where notice.id = :id")
    void updateUseStatus(@Param("id") Long id, @Param("useYn") String useYn);


//    @Modifying
//    @Query("update User u set u.point = m.point + :reward")
//    int plusPointAll(@Param("reward") int reward);
}
