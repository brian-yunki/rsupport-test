package rsupport.test.domain.notice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.test.config.StorageConfig;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.model.Attachment;
import rsupport.test.domain.notice.model.Notice;
import rsupport.test.domain.notice.model.NoticeSearch;
import rsupport.test.domain.notice.repository.AttachmentRepository;
import rsupport.test.domain.notice.repository.NoticeRepository;
import rsupport.test.domain.support.Converter;
import rsupport.test.exception.CustomException;
import rsupport.test.exception.ErrorCode;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final AttachmentRepository attachmentRepository;

    private final ObjectMapper objectMapper;


    // search notice data
    public Page<Notice> searchBy(Map<String, String> params, Pageable pageable) {
        NoticeSearch noticeSearch = objectMapper.convertValue(params, NoticeSearch.class);
        List<Notice> notices = Converter.toNoticeModel(noticeRepository.searchBy(noticeSearch, pageable));
        Long count = noticeRepository.searchByCount(noticeSearch);
        return new PageImpl<>(notices, pageable, count);
    }

    // get notice
    public Notice selectById(Long id) {
       NoticeEntity noticeEntity = noticeRepository.selectById(id).stream()
               .findFirst()
               .filter(e -> !"N".equals(e.getUseYn()))
               .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT));
       return Converter.toNoticeModel(List.of(noticeEntity)).getFirst();
    }


    // delete (disable) notice by id
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        NoticeEntity noticeEntity = noticeRepository.findById(id).stream()
                .findFirst()
                .filter(entity -> !"N".equals(entity.getUseYn()))
                .map(entity -> {
                    entity.setUseYn("N");
                    return entity;
                })
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST, "글이 삭제되었거나 존재하지 않습니다"));
        // important!!
        noticeRepository.flush();

        // attachment bulk update
        //        noticeEntity.getNoticeFileEntities().forEach(e -> e.setUseYn("N")); // N번 쿼리발생으로 벌크로 변경
        attachmentRepository.disableAllByNoticeId(noticeEntity.getId(), noticeEntity.getUpdateId(), noticeEntity.getUpdateDate());
        attachmentRepository.flush();
    }

    // save notice
    @Transactional(rollbackFor = Exception.class)
    public void save(Notice notice) {
        noticeRepository.saveAll(Converter.toNoticeEntities(List.of(notice)));
    }

    // update notice
    @Transactional(rollbackFor = Exception.class)
    public void update(Notice notice) {
        noticeRepository.saveAll(Converter.toNoticeEntities(List.of(notice)));
    }

    // get notice files by notice id
    public List<Attachment> selectNoticeFiles(Long id) {
        return Converter.toAttachmentModel(attachmentRepository.selectByNoticeId(id));
    }

}
