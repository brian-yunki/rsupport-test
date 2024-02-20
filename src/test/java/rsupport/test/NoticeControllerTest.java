package rsupport.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import rsupport.test.notice.entity.NoticeEntity;
import rsupport.test.notice.repository.NoticeRepository;
import java.time.LocalDateTime;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@Import(TestContainersConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoticeControllerTest {

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


    @Test
    void findNoticeList_WithSize() {
        LocalDateTime now = LocalDateTime.now();
        repository.saveAll(List.of(
                NoticeEntity.builder().title("TEST1").subject("테스트1").useYn("Y").createId("jeon.yunki").createDate(now).viewCount(0L).startDate(now).endDate(now).build(),
                NoticeEntity.builder().title("TEST2").subject("테스트2").useYn("Y").createId("jeon.yunki").createDate(now).viewCount(0L).startDate(now).endDate(now).build(),
                NoticeEntity.builder().title("TEST3").subject("테스트3").useYn("Y").createId("jeon.yunki").createDate(now).viewCount(0L).startDate(now).endDate(now).build()
        ));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/notice")
                .then()
                .statusCode(200)
                .body(".", hasSize(3));
    }
}
