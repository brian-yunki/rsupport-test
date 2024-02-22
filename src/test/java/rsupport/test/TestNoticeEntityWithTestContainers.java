package rsupport.test;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.entity.NoticeFileEntity;
import rsupport.test.domain.notice.repository.NoticeRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static rsupport.test.domain.notice.entity.QNoticeEntity.noticeEntity;

@Slf4j
@SpringBootTest
@Import(TestContainersConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class TestNoticeEntityWithTestContainers {

    @Autowired
    private MySQLContainer<?> mySQLContainer;

    @Autowired
    private EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    private NoticeRepository repository;

    @BeforeAll
    void init() {
        mySQLContainer.start();
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }


    @Test
    void testConnection_ForMySqlContainer(){
        Assertions.assertTrue(mySQLContainer.isRunning());
        Assertions.assertEquals("jeon.yunki", mySQLContainer.getUsername());
        Assertions.assertEquals("1q2w3e4r!", mySQLContainer.getPassword());
        Assertions.assertEquals("rsupport_test", mySQLContainer.getDatabaseName());
    }


    @DisplayName("공지사항 데이터 입력 (파일없이)")
    @Test
    public void insert_notice_date_with_files() {
        // # Hibernate 6.0  After
//        jpaQueryFactory.insert(noticeEntity)
//                .columns(noticeEntity.title, noticeEntity.subject, noticeEntity.createId,
//                        noticeEntity.startDate, noticeEntity.endDate, noticeEntity.useYn, noticeEntity.viewCount, noticeEntity.createDate)
//                .values(notice.getTitle(), notice.getSubject(), notice.getCreateId(),
//                        notice.getStartDate(), notice.getEndDate(), notice.getUseYn(), notice.getViewCount(), LocalDateTime.now())
//                .execute();
//
        List<NoticeEntity> noticeEntityList = dummyNoticeData(DATA_SAZE);
        noticeEntityList.forEach(notice -> {
            notice.setNoticeFileEntities(dummyNoticeFileData((int)(Math.random() * 10 +1)));
        });

        // recommand this style!
//        noticeEntityList.forEach(notice -> entityManager.persist(notice));

        // or
        repository.saveAll(noticeEntityList);
    }

    @DisplayName("입력이 잘되는지 테스트")
    @Test
    public void select_before_insert_data() {

        // 데이터 입력
        insert_notice_date_with_files();

        // 조회
        List<NoticeEntity> noticeEntityList = jpaQueryFactory.selectFrom(noticeEntity)
                .where(noticeEntity.useYn.eq("Y"))
                .fetch();

        Assertions.assertEquals(DATA_SAZE, noticeEntityList.size());
    }











    // -- helders


    private static final int DATA_SAZE = 5;

    private List<NoticeEntity> dummyNoticeData(int size) {
        LocalDateTime now = LocalDateTime.now();
        List<NoticeEntity> noticeEntityList = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            noticeEntityList.add(NoticeEntity.builder()
                    .title("TEST" + i).subject("테스트" + i)
                    .useYn("Y")
                    .createId("jeon.yunki")
//                    .createDate(now)
                    .viewCount(0L)
                    .startDate(now)
                    .endDate(now)
                    .build());
        }
        return noticeEntityList;
    }


    private List<NoticeFileEntity> dummyNoticeFileData(int size) {
        List<NoticeFileEntity> noticeFileEntityList = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            noticeFileEntityList.add(NoticeFileEntity.builder()
                    .name("TEST1")
                    .path("/tmp/" + UUID.randomUUID().toString() + ".png")
                    .createId("jeon.yunki")
                    .useYn("Y")
                    .size(2048L)
                    .build());
        }

        return noticeFileEntityList;
    }


}
