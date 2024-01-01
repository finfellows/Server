package com.finfellows.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Jackson 역직렬화를 위한 기본 생성자
public class TokenMapping {

    private String email;
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenMapping(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
