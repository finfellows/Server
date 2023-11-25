package com.finfellows.global.config.security.token;

import com.finfellows.domain.user.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails {

    private final User user;

    private final Long id;
    private final String email;
    private final String name;
    private Map<String, Object> attributes;

    public UserPrincipal(User user, Long id, String email, String name, String userName) {
        this.user = user;
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static UserPrincipal createUser(User user) {
        return new UserPrincipal(
                user,
                user.getId(),
                user.getProviderId(),
                user.getEmail(),
                user.getName()
        );
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }
}
