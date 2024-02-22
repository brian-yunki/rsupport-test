package rsupport.test.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.test.domain.notice.entity.NoticeFileEntity;
import rsupport.test.domain.notice.repository.NoticeFileRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeFIleService {

    private final NoticeFileRepository noticeFileRepository;

    public List<NoticeFileEntity> findAll() {
        return noticeFileRepository.findAll();
    }
}


