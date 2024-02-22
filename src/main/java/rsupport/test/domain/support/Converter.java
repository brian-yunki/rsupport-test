package rsupport.test.domain.support;

import rsupport.test.domain.notice.entity.AttachmentEntity;
import rsupport.test.domain.notice.entity.NoticeEntity;
import rsupport.test.domain.notice.model.Attachment;
import rsupport.test.domain.notice.model.Notice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Converter {

    public static List<NoticeEntity> toNoticeEntities(List<Notice> notices) {
        return notices.stream()
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
                .collect(Collectors.toList());
    }

    public static List<Notice> toNoticeModel(List<NoticeEntity> entities) {
        return entities.stream()
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
                .collect(Collectors.toList());
    }

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
                        .build())
                .collect(Collectors.toList());
    }
}
