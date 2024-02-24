package rsupport.test.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rsupport.test.domain.member.repository.MemberRepository;


@Service
@RequiredArgsConstructor
public class SecurityUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUserId(username)
                .map(member -> SecurityUserDetails.builder()
                        .username(member.getUserId())
                        .password(member.getPassword())
                        .authority(member.getRole().getAuthority())
                        .useYn(member.getUseYn().equals("Y"))
                        .build()
                )
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
    }
}
