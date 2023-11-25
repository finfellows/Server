package com.finfellows.global.config.security;

import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String providerId = authentication.getCredentials().toString();
        System.out.println("providerId = " + providerId);

        UserDetails user = userRepository.findByProviderId(providerId)
                .map(UserPrincipal::createUser)
                .orElseThrow(() -> new UsernameNotFoundException("Provider ID not found: " + providerId));

        return new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
