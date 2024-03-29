package rsupport.test.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import rsupport.test.config.StorageProperties;

import java.io.IOException;
import java.nio.file.*;

@Slf4j
@Component
@EnableConfigurationProperties(StorageProperties.class)
@RequiredArgsConstructor
public class StartupRunner implements ApplicationRunner {

    private final StorageProperties storageProperties;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Path directory = Paths.get(storageProperties.getPath());
        try{
            Files.createDirectory(directory);
        } catch (FileAlreadyExistsException e) {
            log.info("upload directory is exist");
        } catch (NoSuchFileException e) {
            log.error("upload directory is not found");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
