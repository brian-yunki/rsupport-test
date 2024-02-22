package rsupport.test.domain.notice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.repository.NoticeRepository;
import rsupport.test.domain.notice.entity.NoticeFileEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;


    /*
     *
     * @return
     */
    public List<NoticeEntity> getAll() {
        return noticeRepository.findAll();
    }

    public List<NoticeEntity> getById(Long id) {
       return noticeRepository.findById(id).stream().collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        noticeRepository.deleteById(id);
    }
}
