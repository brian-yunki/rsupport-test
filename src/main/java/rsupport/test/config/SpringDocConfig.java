package rsupport.test.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(generateComponents())
                .info(new Info().title("NOTICE API")
                        .description("R SUPPORT TEST")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("https://github.com/brian-yunki/rsupport-test")
                        .url("https://github.com/brian-yunki/rsupport-test"))
                ;
    }

    private Components generateComponents() {
        return new Components()
                // 인증헤더 설정
                .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }
}
