package rsupport.test.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import rsupport.test.config.security.token.Token;
import rsupport.test.config.security.token.TokenService;
import rsupport.test.domain.member.model.Account;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public Token authenticate(Account account) throws AuthenticationException {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(account.getUserId(), account.getPassword()));

        // (authenticate) after.
        // 1. provider.retrieveUser -> detailservice.loadUserByUsername
        // 2. provider.additionalAuthenticationChecks (password validate check)

        return tokenService.generateLoginToken(authentication);
    }

}
