package rsupport.test.domain.notice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.NoticeEntity;
import static rsupport.test.domain.notice.entity.QNoticeEntity.noticeEntity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepositoryImpl implements NoticeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public List<NoticeEntity> findAllByUseYnEqualsIsTrue() {
        return jpaQueryFactory.selectFrom(noticeEntity)
                .innerJoin(noticeEntity.noticeFileEntities)
                .fetchJoin()
                .where(noticeEntity.useYn.eq("Y"))
                .fetch();
    }

}
