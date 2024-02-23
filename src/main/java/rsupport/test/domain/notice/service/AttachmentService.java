package rsupport.test.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.test.domain.notice.entity.AttachmentEntity;
import rsupport.test.domain.notice.repository.AttachmentRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public List<AttachmentEntity> findAll() {
        return attachmentRepository.findAll();
    }
}


