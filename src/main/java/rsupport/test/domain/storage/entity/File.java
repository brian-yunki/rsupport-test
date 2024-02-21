package rsupport.test.domain.storage.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import rsupport.test.domain.notice.entity.Notice;
import rsupport.test.domain.support.Aduit;

@Entity
@Table(name = "NOTICE_FILE")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class File extends Aduit {

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

    @Column(name = "USE_YN", nullable = false)
    private String useYn;

    @ManyToOne
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;

}
