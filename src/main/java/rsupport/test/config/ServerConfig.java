package rsupport.test.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ServerConfig {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(connector.apply(8080));
        return tomcatServletWebServerFactory;
    }

    private Function<Integer, Connector> connector = (port) -> {
        Connector connector =  new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port);
        return connector;
    };

}
