package rsupport.test.domain.notice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.model.NoticeSearch;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import static org.springframework.util.StringUtils.hasText;
import static rsupport.test.domain.notice.entity.QNoticeEntity.noticeEntity;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepositoryImpl implements NoticeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // notiec orderby condition
    Function<Pageable, OrderSpecifier<?>> orderBy = (pageable) -> {
        if (pageable.getSort().isEmpty())
            return null;
        for (Sort.Order order : pageable.getSort()) {
            Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            if (order.getProperty().equals("createDate")) {
                return new OrderSpecifier<>(direction, noticeEntity.createDate);
            }
        }
        return null;
    };

    // Notice search condition
    Function<NoticeSearch, BooleanBuilder> condition = (condition) -> {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getTitle())) {
            builder.and(noticeEntity.title.contains(condition.getTitle()));
        }
        if (condition.getStart() != null && condition.getEnd() != null) {
            builder.and(noticeEntity.startDate.between(condition.getStart(), condition.getEnd()));
        }
        return builder;
    };


    public List<NoticeEntity> searchBy(NoticeSearch noticeSearch, Pageable pageable) {
        return jpaQueryFactory.selectFrom(noticeEntity)
                .innerJoin(noticeEntity.attachments)
                .fetchJoin()
                .where(noticeEntity.useYn.eq("Y").and(condition.apply(noticeSearch)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderBy.apply(pageable))
                .fetch();
    }

    public Long searchByCount(NoticeSearch noticeSearch) {
        return jpaQueryFactory.select(noticeEntity.count())
                .from(noticeEntity)
                .where(noticeEntity.useYn.eq("Y").and(condition.apply(noticeSearch)))
                .fetchOne();
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

    public Long count(Long id) {
        return jpaQueryFactory.select(noticeEntity.count())
                .from(noticeEntity)
                .where(noticeEntity.id.eq(id))
                .fetchOne();
    }

}
