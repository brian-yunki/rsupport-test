package rsupport.test.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "storage")
@Getter @Setter
public class StorageConfig {
    private String path;
    private List<String> allowFileExtension;
    private List<String> allowMimeType;
}
