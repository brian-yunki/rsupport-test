package rsupport.test.domain.member.model;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends Account {

    @Size(min = 2, max = 6, message = "이름은 2글자 이상, 6글자 이하로 입력해주세요.")
    public String name;

}
