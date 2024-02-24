package rsupport.test.domain.support;

import rsupport.test.domain.notice.entity.AttachmentEntity;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.model.Attachment;
import rsupport.test.domain.notice.model.Notice;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import static rsupport.test.support.Utils.calculatedPoolSize;

/*
 * MapStruct 등의 라이브러리 사용하지 않음
 * 구조 복잡할 경우 비용상승
 * Entity to DTO 매핑할 경우 지연로딩이 예상과 다르게 작동
 * 라이브러리 사용하는 것 보다 성능이 좋음
 */
public class Converter {

    // Notice model to entity mapping
    public static List<NoticeEntity> toNoticeEntities(List<Notice> notices) {
           try (ForkJoinPool pool = new ForkJoinPool(calculatedPoolSize.apply(2))) {
               return pool.submit(() -> notices.stream()
                       .parallel() // parallel
                       .map(notice -> {
                            NoticeEntity entity = NoticeEntity.builder()
                                    .title(notice.getTitle())
                                    .subject(notice.getSubject())
                                    .startDate(notice.getStartDate())
                                    .endDate(notice.getEndDate())
                                    .viewCount(Optional.ofNullable(notice.getViewCount()).orElse(0L))
                                    .useYn(Optional.ofNullable(notice.getUseYn()).orElse("Y"))
                                    .build();
                            entity.setAttachmentList(toAttachmentEntities(notice.getAttachments()));
                            return entity;
                       })
                       .collect(Collectors.toList()))
                       .get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    // notice entity to model mapping
    public static List<Notice> toNoticeModel(List<NoticeEntity> entities) {
        try (ForkJoinPool pool = new ForkJoinPool(calculatedPoolSize.apply(2))) {
            return pool.submit(() -> entities.stream()
                    .parallel() // parallel
                    .map(entity -> Notice.builder()
                            .id(entity.getId())
                            .title(entity.getTitle())
                            .subject(entity.getSubject())
                            .startDate(entity.getStartDate())
                            .endDate(entity.getEndDate())
                            .viewCount(entity.getViewCount())
                            .useYn(entity.getUseYn())
                            .createId(entity.getCreateId())
                            .createDate(entity.getCreateDate())
                            .updateId(entity.getUpdateId())
                            .updateDate(entity.getUpdateDate())
                            .attachments(toAttachmentModel(entity.getAttachments()))
                            .build())
                    .collect(Collectors.toList()))
                    .get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // attachment model to entity mapping
    public static List<AttachmentEntity> toAttachmentEntities(List<Attachment> attachments) {
        return attachments.stream()
                .map(attachment -> AttachmentEntity.builder()
                        .name(attachment.getName())
                        .path(attachment.getPath())
                        .size(attachment.getSize())
                        .useYn(Optional.ofNullable(attachment.getUseYn()).orElse("Y"))
                        .build())
                .collect(Collectors.toList());
    }

    // attachment entity to model mapping
    public static List<Attachment> toAttachmentModel(List<AttachmentEntity> entities) {
        return entities.stream()
                .map(entity -> Attachment.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .path(entity.getPath())
                        .size(entity.getSize())
                        .useYn(entity.getUseYn())
                        .createId(entity.getCreateId())
                        .createDate(entity.getCreateDate())
                        .updateId(entity.getUpdateId())
                        .updateDate(entity.getUpdateDate())
                        .noticeId(entity.getNotice().getId())
                        .build())
                .collect(Collectors.toList());
    }
}
