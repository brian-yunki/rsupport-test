package rsupport.test.domain.notice.model;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class Attachment {

    private Long id;
    private String name;
    private String path;
    private Long size;
    private String useYn;
    private String createId;
    private LocalDateTime createDate;
    private String updateId;
    private LocalDateTime updateDate;
}
