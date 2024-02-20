package rsupport.test;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfiguration {

    @Bean
    @ServiceConnection
    public MySQLContainer<?> mySQLContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:8.3.0"))
                .withUsername("jeon.yunki")
                .withPassword("1q2w3e4r!")
                .withDatabaseName("rsupport_test")
                .withInitScript("schema.sql")
                .withReuse(true);
    }
}
