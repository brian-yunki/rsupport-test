package rsupport.test.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .components(generateComponents())
                .info(new Info().title("NOTICE API")
                        .description("R SUPPORT TEST - jeon.yunki@gmail.com")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("https://github.com/brian-yunki/rsupport-test")
                        .url("https://github.com/brian-yunki/rsupport-test"))
                ;
    }

    private Components generateComponents() {
        return new Components()
                // BASIC
//                .addSecuritySchemes("basic",
//                        new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("Basic")
//                )
                // 인증헤더 설정
                .addSecuritySchemes(HttpHeaders.AUTHORIZATION,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }

    static {
        // ingnore swagger parameter
        // https://springdoc.org/faq.html#how-can-i-hide-a-parameter-from-the-documentation-
        SpringDocUtils.getConfig()
                .addSimpleTypesForParameterObject()
                .addRequestWrapperToIgnore(
                        Pageable.class,
                        Specification.class,
                        Map.class,
                        RequestParam.class
                )
        ;
    }
}
