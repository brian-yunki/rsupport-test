package rsupport.test.domain.notice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import rsupport.test.support.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "NOTICE")
@DynamicUpdate
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "VIEW_COUNT", nullable = false)
    private Long viewCount;

    @Setter
    @Column(name = "USE_YN", nullable = false)
    private String useYn;

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttachmentEntity> attachments = new ArrayList<>();

    public void setAttachmentList(List<AttachmentEntity> noticeAttachmentEntityEntities) {
        noticeAttachmentEntityEntities.forEach(file -> file.setNotice(this));
        this.attachments.addAll(noticeAttachmentEntityEntities);
    }

}