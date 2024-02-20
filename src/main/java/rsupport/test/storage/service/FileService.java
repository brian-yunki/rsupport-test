package rsupport.test.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.test.storage.entity.FileEntity;
import rsupport.test.storage.repository.FileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public List<FileEntity> findAll() {
        return fileRepository.findAll();
    }
}
