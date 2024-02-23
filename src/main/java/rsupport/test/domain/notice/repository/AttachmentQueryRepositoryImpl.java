package rsupport.test.domain.notice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

import static rsupport.test.domain.notice.entity.QAttachmentEntity.attachmentEntity;

@Repository
@RequiredArgsConstructor
public class AttachmentQueryRepositoryImpl implements AttachmentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    @Override
    public Long disableAllByNoticeId(Long noticeId, String updateId, LocalDateTime updateDate) {
        return jpaQueryFactory.update(attachmentEntity)
                .set(List.of(attachmentEntity.useYn, attachmentEntity.updateId, attachmentEntity.updateDate), List.of("N", updateId, updateDate))
                .where(attachmentEntity.notice.id.eq(noticeId).and(attachmentEntity.useYn.eq("Y")))
                .execute();
    }
}
