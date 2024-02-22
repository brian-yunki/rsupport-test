package rsupport.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.entity.NoticeFileEntity;
import rsupport.test.domain.notice.repository.NoticeRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static rsupport.test.domain.support.JsonHelper.prettyPrintJsonUsingDefaultPrettyPrinter;

@Slf4j
@Import(TestContainersConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestNotice {


    @LocalServerPort
    private Integer port;

    @Autowired
    private NoticeRepository repository;

    @Autowired
    MySQLContainer<?> mySQLContainer;

    @BeforeAll
    void setup() {
        mySQLContainer.start();
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void testConnection_ForMySqlContainer(){
        Assertions.assertTrue(mySQLContainer.isRunning());
        Assertions.assertEquals("jeon.yunki", mySQLContainer.getUsername());
        Assertions.assertEquals("1q2w3e4r!", mySQLContainer.getPassword());
        Assertions.assertEquals("rsupport_test", mySQLContainer.getDatabaseName());
    }

    List<NoticeEntity> dummyNoticeData(int size) {
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

    List<NoticeFileEntity> dummyNoticeFileData(int size) {
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


    @Test
    void insert_notice_without_files() {
        repository.saveAll(dummyNoticeData(5));
    }


    @Test
    void insert_notice_with_files() {
        List<NoticeEntity> noticeEntityList = dummyNoticeData(5);
        noticeEntityList.forEach(notice -> {
            notice.setNoticeFileEntities(dummyNoticeFileData((int)(Math.random() * 10 +1)));
        });
        repository.saveAll(noticeEntityList);
    }



    @Test
    void get_notice() throws JsonProcessingException {

        // 공지사항 입력 (with file info)
        insert_notice_with_files();

        // 조회
        String result = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/notices")
                .getBody().asString();

        log.debug(prettyPrintJsonUsingDefaultPrettyPrinter(result));
    }
}
