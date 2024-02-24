package rsupport.test.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rsupport.test.config.security.token.Token;
import rsupport.test.domain.member.model.Account;
import rsupport.test.domain.member.service.LoginService;


@Tag(name = "로그인")
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @Operation(summary = "로그인 (JWT 발급)")
    @PostMapping("/login")
    public Token login(@Valid @RequestBody Account account) throws AuthenticationException {
        return loginService.authenticate(account);
    }
}
