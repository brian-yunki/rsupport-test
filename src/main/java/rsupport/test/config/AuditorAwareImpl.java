package rsupport.test.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AuditorAwareImpl implements AuditorAware<String>  {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Auditing @CreatedBy @LastModifiedBy 로 사용자 정보를 넘겨주도록 구현
        // SpringSecurity 의 사용자 정보를 가져와 전달할 수 있도록 구현해야 하지만 이 과제에서는 생략
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        authentication.getPrincipal();
        return Optional.ofNullable("jeon.yunki");
    }
}
