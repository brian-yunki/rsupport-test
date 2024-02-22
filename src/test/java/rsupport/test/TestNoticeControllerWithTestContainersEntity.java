package rsupport.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

import static io.restassured.RestAssured.given;
import static rsupport.test.domain.support.JsonHelper.prettyPrintJsonUsingDefaultPrettyPrinter;


@Slf4j
@Import({TestContainersConfiguration.class,
        TestNoticeWithTestContainersEntity.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestNoticeControllerWithTestContainersEntity {

    @LocalServerPort
    private Integer port;

    @Autowired
    MySQLContainer<?> mySQLContainer;


    @Autowired
    private TestNoticeWithTestContainersEntity testNoticeWithTestContainers;

    @BeforeAll
    void init() {
        mySQLContainer.start();
    }

    @BeforeEach
    void before_start() {
        RestAssured.baseURI = "http://localhost:" + port;
    }


    @Test
    void get_notice_list() throws JsonProcessingException {

        // 데이터 입력
        testNoticeWithTestContainers.insert_notice_date_with_files();

        // 조회
        String result =
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/notices")
                .getBody()
                .asString();

        // 확인용 출력 (option)
        log.debug(prettyPrintJsonUsingDefaultPrettyPrinter(result));
    }

    @Test
    void delete_notice_by_id() {
        // 데이터 입력
        testNoticeWithTestContainers.insert_notice_date_with_files();
    }


}
