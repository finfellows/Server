package com.finfellows.domain.auth.application;

import com.finfellows.domain.auth.domain.Token;
import com.finfellows.domain.auth.domain.repository.TokenRepository;
import com.finfellows.domain.auth.dto.RefreshTokenReq;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.DefaultAssert;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.error.DefaultAuthenticationException;
import com.finfellows.global.payload.ErrorCode;
import com.finfellows.global.payload.Message;
import com.finfellows.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Transactional
    public ResponseCustom<?> whoAmI(UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isOptionalPresent(user);
        return ResponseCustom.OK(user);
    }

    @Transactional
    public Message signOut(final RefreshTokenReq tokenRefreshRequest) {
        Token token = tokenRepository.findByRefreshToken(tokenRefreshRequest.getRefreshToken())
                .orElseThrow(() -> new DefaultAuthenticationException(ErrorCode.INVALID_AUTHENTICATION));
        tokenRepository.delete(token);

        return Message.builder()
                .message("로그아웃 하였습니다.")
                .build();
    }

    @Transactional
    public Message deleteAccount(UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findById(userPrincipal.getId());
        DefaultAssert.isTrue(user.isPresent(), "유저가 올바르지 않습니다.");

        Optional<Token> token = tokenRepository.findByEmail(userPrincipal.getEmail());
        DefaultAssert.isTrue(token.isPresent(), "토큰이 유효하지 않습니다.");

        userRepository.delete(user.get());
        tokenRepository.delete(token.get());


        return Message.builder()
                .message("회원 탈퇴 하였습니다.")
                .build();
    }
}
