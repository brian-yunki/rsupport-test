package rsupport.test.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.test.domain.notice.entity.Attachment;
import rsupport.test.domain.notice.repository.AttachmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public List<Attachment> findAll() {
        return attachmentRepository.findAll();
    }
}


