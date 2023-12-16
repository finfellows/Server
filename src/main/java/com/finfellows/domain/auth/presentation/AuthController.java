package com.finfellows.domain.auth.presentation;

import com.finfellows.domain.auth.application.KakaoService;
import com.finfellows.domain.auth.dto.AuthRes;
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

    @Operation(summary = "카카오 code 발급", description = "카카오 API 서버에 접근 권한을 인가하는 code를 발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "code 발급 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthRes.class))}),
            @ApiResponse(responseCode = "400", description = "code 발급 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "/login")
    public void socialLoginRedirect() throws IOException {
        kakaoService.accessRequest();
    }

    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AuthRes.class))}),
            @ApiResponse(responseCode = "400", description = "로그인 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "/kakao/login")
    public ResponseCustom<?> kakaoCallback(
            @Parameter(description = "code를 입력해주세요.", required = true) @RequestParam("code") String code
    ) {
        String accessToken = kakaoService.getKakaoAccessToken(code);
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);



        return ResponseCustom.OK(kakaoService.kakaoLogin(kakaoProfile));

    }





}
