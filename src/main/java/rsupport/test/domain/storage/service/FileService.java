package rsupport.test.domain.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.test.domain.storage.entity.File;
import rsupport.test.domain.storage.repository.FileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public List<File> findAll() {
        return fileRepository.findAll();
    }
}
