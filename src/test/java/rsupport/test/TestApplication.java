package rsupport.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@Import(TestContainersConfiguration.class)
@TestConfiguration(proxyBeanMethods = false)
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main).with(TestApplication.class).run(args);
    }

}
