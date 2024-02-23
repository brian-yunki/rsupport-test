package rsupport.test.domain.notice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rsupport.test.domain.notice.entity.NoticeEntity;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    /*
     * 리스트조회
     * @return
     */
    public List<Notice> searchBy(Map<String, String> params, Pageable pageable) {
        NoticeSearch noticeSearch = objectMapper.convertValue(params, NoticeSearch.class);

        List<NoticeEntity> entities = noticeRepository.searchBy(noticeSearch, pageable);
        return Converter.toNoticeModel(entities);
    }

    public Notice selectById(Long id) {
       NoticeEntity noticeEntity = noticeRepository.selectById(id).stream()
               .findFirst()
               .filter(e -> !"N".equals(e.getUseYn()))
               .orElseThrow(() -> new CustomException(ErrorCode.SERVER_ERROR, "존재하지 않는 공지입니다."));
       return Converter.toNoticeModel(List.of(noticeEntity)).getFirst();
    }


    /*
     * 글삭제
     * - 공지글 조회, 조회 시 등록된 글인지, 등록되었지만 삭제된 글인지를 확인 후 오류 반환
     * - 등록한 글의 상태 'USE_YN' 컬럼값을 'N'으로 변경
     * - 등록한 글의 첨부파일의 상태 'USE_YN' 을 'N' 으로 변경
     *
     * 공지사항의 글 삭제기능은 빈번히 일어나는 기능이 아니므로 쿼리 최적화 보다는 구현 편의에 관점을 둠
     * JPA 영속성 컨텍스트내에서 Adiuting 의 정상적인 기능을 위해서 Repository save 기능으로 저장/수정이 이뤄져야 하기 때문에 JPQL이나 QueryDSL을 사용하지 않음.
     * 또한 업데이트 대상에 대한 감지를 통한 변경사항 만을 변경하기 위해 Entity에 @DynamicUpdate (Hibernate) 어노네이션으로 전체 업테이트가 아닌 변경사항만 업데이트 되도록 설정
     * 다만 동일 유형의 기능인 조회 수 증가에 대해서는 JPQL 또는 QueryDSL을 사용하여 조회수 업데이트를 구현
     *
     * @param id
     */
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
        // 반듯이
        noticeRepository.flush();

        // 첨부파일 벌크업데이트
        //        noticeEntity.getNoticeFileEntities().forEach(e -> e.setUseYn("N")); // N번 쿼리로 변경
        attachmentRepository.disableAllByNoticeId(noticeEntity.getId(), noticeEntity.getUpdateId(), noticeEntity.getUpdateDate());
        attachmentRepository.flush();
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(Notice notice) {
        noticeRepository.saveAll(Converter.toNoticeEntities(List.of(notice)));
    }


    @Transactional(rollbackFor = Exception.class)
    public void update(Notice notice) {
        noticeRepository.saveAll(Converter.toNoticeEntities(List.of(notice)));
    }




}
