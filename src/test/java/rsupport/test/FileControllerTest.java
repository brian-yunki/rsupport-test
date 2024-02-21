package rsupport.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import rsupport.test.domain.storage.entity.File;
import rsupport.test.domain.storage.repository.FileRepository;

import java.time.LocalDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@Import(TestContainersConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private FileRepository repository;

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
    void findFileList_WhenDoNotHaveAnyRelation() {
        LocalDateTime now = LocalDateTime.now();
        repository.saveAll(List.of(
                File.builder().name("TEST1").path("/tmp/xxxx.jpg").useYn("Y").createId("jeon.yunki").createDate(now).size(2048L).build(),
                File.builder().name("TEST2").path("/tmp/yyyy.png").useYn("Y").createId("jeon.yunki").createDate(now).size(1024L).build()
        ));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/file")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }
}
