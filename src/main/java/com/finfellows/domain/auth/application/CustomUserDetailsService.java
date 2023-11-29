package com.finfellows.domain.auth.application;


import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return UserPrincipal.createUser(user.get());
        }

        throw new UsernameNotFoundException("유효하지 않는 유저입니다.");
    }

    public UserDetails loadUserById(Long id) {
//        Optional<User> user = userRepository.findById(id);
        Optional<User> user = userRepository.findByProviderId(Long.toString(id));
        if (user.isPresent()) {
            return UserPrincipal.createUser(user.get());
        }

        throw new UsernameNotFoundException("유효하지 않는 유저입니다.");
    }






}
