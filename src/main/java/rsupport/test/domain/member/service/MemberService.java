package rsupport.test.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rsupport.test.config.security.SecurityUserDetails;
import rsupport.test.domain.member.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;

    public User findByUserId(SecurityUserDetails principal) throws NullPointerException {
        if (Optional.ofNullable(principal).isEmpty()) {
            throw new BadCredentialsException("사용자 인증 정보가 없습니다.");
        }
        return User.builder().build();
    }
  }
