package rsupport.test.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"rsupport.test.domain"})
public class JpaConfig {
}
