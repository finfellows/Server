package com.finfellows.domain.auth.presentation;

import com.finfellows.domain.auth.application.KakaoService;
import com.finfellows.domain.auth.dto.KakaoProfile;
import com.finfellows.domain.auth.dto.TokenMapping;
import com.finfellows.global.payload.ErrorResponse;
import com.finfellows.global.payload.Message;
import com.finfellows.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Authorization", description = "Authorization API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final KakaoService kakaoService;

    // 카카오 code 발급, 로그인 인증으로 리다이렉트해주는 url
    @GetMapping("/login")
    public void socialLoginRedirect() throws IOException {
        kakaoService.accessRequest();
    }

    // 카카오 로그인
    @GetMapping("/kakao/login")
    public ResponseCustom<?> kakaoCallback(@RequestParam("code") String code) {
        String accessToken = kakaoService.getKakaoAccessToken(code);
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);



        return ResponseCustom.OK(kakaoService.kakaoLogin(kakaoProfile));

    }



}
