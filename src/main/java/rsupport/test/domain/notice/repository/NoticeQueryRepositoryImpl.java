package rsupport.test.domain.notice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rsupport.test.domain.notice.entity.Notice;
import static rsupport.test.domain.notice.entity.QNotice.notice;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepositoryImpl implements NoticeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public List<Notice> findAllByUseYnEqualsIsTrue() {
        return jpaQueryFactory.selectFrom(notice)
                .innerJoin(notice.attachments)
                .fetchJoin()
                .where(notice.useYn.eq("Y"))
                .fetch();
    }

    @Transactional
    @Override
    public Long updateCount(Long id) {
        return jpaQueryFactory.update(notice)
                .set(notice.viewCount, notice.viewCount.add(1))
                .where(notice.id.eq(id))
                .execute();

    }


}
