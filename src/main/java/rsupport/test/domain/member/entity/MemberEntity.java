package rsupport.test.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import rsupport.test.config.security.enums.RoleType;
import rsupport.test.support.entity.BaseEntity;

@Entity
@Table(name = "MEMBER")
@DynamicUpdate
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @NotNull
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NotNull
    @Column(name = "NAME",  nullable = false)
    private String name;

    @Column(name = "ROLE",   nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoleType role;

    @Column(name = "USE_YN", nullable = false)
    @ColumnDefault("'Y'")
    private String useYn;
}
