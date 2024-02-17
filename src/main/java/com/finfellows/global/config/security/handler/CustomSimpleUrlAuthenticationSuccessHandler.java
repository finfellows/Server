package com.finfellows.global.config.security.handler;

import com.finfellows.domain.auth.application.CustomTokenProviderService;
import com.finfellows.domain.auth.dto.TokenMapping;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.DefaultAssert;
import com.finfellows.global.config.security.OAuth2Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomTokenProviderService customTokenProviderService;
    private final OAuth2Config oAuth2Config;
    private final UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DefaultAssert.isAuthentication(!response.isCommitted());

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        TokenMapping token = customTokenProviderService.createToken(authentication);
        String userEmail = token.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        String providerId = userOptional.get().getProviderId();



        response.sendRedirect("http://localhost:3000/auth/oauth-response/" + token + "/3600" + "/providerId:" + providerId);

    }
}
