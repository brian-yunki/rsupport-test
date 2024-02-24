package rsupport.test.domain.notice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import rsupport.test.domain.notice.entity.AttachmentEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static rsupport.test.domain.notice.entity.QAttachmentEntity.attachmentEntity;

@Repository
@RequiredArgsConstructor
public class AttachmentQueryRepositoryImpl implements AttachmentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;


    // select attachement by attachment id
    public Optional<AttachmentEntity> selectById(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(attachmentEntity)
                .innerJoin(attachmentEntity.notice)
                .fetchJoin()
                .where(attachmentEntity.useYn.eq("Y").and(attachmentEntity.id.eq(id)))
                .fetchOne());
    }


    // select attachment list by notice id
    public List<AttachmentEntity> selectByNoticeId(Long id) {
        return jpaQueryFactory.selectFrom(attachmentEntity)
                .innerJoin(attachmentEntity.notice)
                .fetchJoin()
                .where(attachmentEntity.useYn.eq("Y").and(attachmentEntity.notice.id.eq(id)))
                .fetch();
    }


    // delete (disable) attachement by notice id
    @Transactional
    @Override
    public void disableAllByNoticeId(Long noticeId, String updateId, LocalDateTime updateDate) {
        jpaQueryFactory.update(attachmentEntity)
                .set(List.of(attachmentEntity.useYn, attachmentEntity.updateId, attachmentEntity.updateDate), List.of("N", updateId, updateDate))
                .where(attachmentEntity.notice.id.eq(noticeId).and(attachmentEntity.useYn.eq("Y")))
                .execute();
    }

}
