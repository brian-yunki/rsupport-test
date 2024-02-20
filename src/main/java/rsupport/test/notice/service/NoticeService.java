package rsupport.test.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rsupport.test.notice.entity.Notice;
import rsupport.test.notice.repository.NoticeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }
}
