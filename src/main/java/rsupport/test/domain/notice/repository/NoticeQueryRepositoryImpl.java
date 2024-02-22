package rsupport.test.domain.notice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rsupport.test.domain.notice.entity.NoticeEntity;
import static rsupport.test.domain.notice.entity.QNoticeEntity.noticeEntity;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepositoryImpl implements NoticeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<NoticeEntity> selectAll() {
        return jpaQueryFactory.selectFrom(noticeEntity)
                .innerJoin(noticeEntity.attachments)
                .fetchJoin()
                .where(noticeEntity.useYn.eq("Y"))
                .fetch();
    }

    public Optional<NoticeEntity> selectById(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(noticeEntity)
                .innerJoin(noticeEntity.attachments)
                .fetchJoin()
                .where(noticeEntity.id.eq(id).and(noticeEntity.useYn.eq("Y")))
                .fetchOne());
    }


    @Transactional
    @Override
    public Long updateCount(Long id) {
        return jpaQueryFactory.update(noticeEntity)
                .set(noticeEntity.viewCount, noticeEntity.viewCount.add(1))
                .where(noticeEntity.id.eq(id))
                .execute();

    }


}
