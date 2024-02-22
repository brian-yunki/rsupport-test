package rsupport.test.domain.notice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static rsupport.test.domain.notice.entity.QAttachment.attachment;

@Repository
@RequiredArgsConstructor
public class AttachmentQueryRepositoryImpl implements AttachmentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    @Override
    public Long disableAll(Long noticeId, String updateId, LocalDateTime updateDate) {
        return jpaQueryFactory.update(attachment)
                .set(List.of(attachment.useYn, attachment.updateId, attachment.updateDate), List.of("N", updateId, updateDate))
                .where(attachment.notice.id.eq(noticeId).and(attachment.useYn.eq("Y")))
                .execute();
    }
}
