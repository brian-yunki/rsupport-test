package rsupport.test.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTICE")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    @Comment("제목")
    private String title;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "VIEW_COUNT", nullable = false)
    private Long viewCount;

    @Column(name = "CREATE_ID", nullable = false)
    private String createId;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "UPDATE_ID")
    private String updateId;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @Column(name = "USE_YN", nullable = false)
    private String useYn;
}
