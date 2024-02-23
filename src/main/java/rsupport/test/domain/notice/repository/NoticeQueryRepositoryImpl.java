package rsupport.test.domain.notice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.model.NoticeSearch;

import static org.springframework.util.StringUtils.hasText;
import static rsupport.test.domain.notice.entity.QNoticeEntity.noticeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import com.querydsl.core.types.Predicate;

@Repository
@RequiredArgsConstructor
public class NoticeQueryRepositoryImpl implements NoticeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<NoticeEntity> searchBy(NoticeSearch noticeSearch, Pageable pageable) {
//        return jpaQueryFactory.selectFrom(noticeEntity)
//                .innerJoin(noticeEntity.attachments)
//                .fetchJoin()
//                .where(noticeEntity.useYn.eq("Y"))
//                .fetch();

        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(noticeSearch.getTitle())) {
            builder.and(noticeEntity.title.contains(noticeSearch.getTitle()));
        }
        if (noticeSearch.getStart() != null && noticeSearch.getEnd() != null) {
            builder.and(noticeEntity.startDate.between(noticeSearch.getStart(), noticeSearch.getEnd()));
        }


        return jpaQueryFactory.selectFrom(noticeEntity)
                .innerJoin(noticeEntity.attachments)
                .fetchJoin()
                .where(noticeEntity.useYn.eq("Y").and(builder))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sortBy.apply(pageable))
                .fetch();
    }

//    private OrderSpecifier<?> boardSort(Pageable page) {
//        if (!page.getSort().isEmpty()) {
//            for (Sort.Order order : page.getSort()) {
//                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
//                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
//                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
//                switch (order.getProperty()){
//                    case "createDate":
//                        return new OrderSpecifier(direction, noticeEntity.createDate);
//                }
//            }
//        }
//        return null;
//    }

    Function<Pageable, OrderSpecifier<?>> sortBy = (pageable) -> {
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



    public Optional<NoticeEntity> selectById(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(noticeEntity)
                .innerJoin(noticeEntity.attachments)
                .fetchJoin()
                .where(noticeEntity.id.eq(id).and(noticeEntity.useYn.eq("Y")))
                .fetchOne());
    }


    public Long count(Long id) {
        return jpaQueryFactory.select(noticeEntity.count())
                .from(noticeEntity)
                .where(noticeEntity.id.eq(id))
                .fetchOne();
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
