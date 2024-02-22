package rsupport.test.domain.notice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import rsupport.test.domain.support.BaseEntity;

@Entity
@Table(name = "NOTICE_FILE")
@DynamicUpdate
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PATH", nullable = false)
    private String path;

    @Column(name = "SIZE", nullable = false)
    private Long size;

    @Setter
    @Column(name = "USE_YN", nullable = false)
    private String useYn;

    @Setter
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "NOTICE_ID", nullable = false)
    private NoticeEntity notice;

}
