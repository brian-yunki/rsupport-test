package rsupport.test.support.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ToString
public abstract class BaseEntity {

    @CreatedBy
    @Column(name = "CREATE_ID", nullable = false, updatable = false)
    private String createId;

    @CreatedDate
    @Column(name = "CREATE_DATE", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedBy
    @Column(name = "UPDATE_ID", insertable = false)
    private String updateId;

    @LastModifiedDate
    @Column(name = "UPDATE_DATE", insertable = false)
    private LocalDateTime updateDate;

}