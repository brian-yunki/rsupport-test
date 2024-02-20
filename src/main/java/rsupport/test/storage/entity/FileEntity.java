package rsupport.test.storage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "NOTICE_FILE")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity {

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

    @Column(name = "CREATE_ID", nullable = false)
    private String createId;

    @Column(name = "CREATE_DATE", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "UPDATE_ID")
    private String updateId;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;
}
