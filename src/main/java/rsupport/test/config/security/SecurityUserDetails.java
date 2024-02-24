package rsupport.test.config.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Builder
public class SecurityUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String authority;
    private final boolean useYn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.useYn;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.useYn;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.useYn;
    }

    @Override
    public boolean isEnabled() {
        return this.useYn;
    }
}
