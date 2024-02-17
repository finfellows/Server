package com.finfellows.global.config;

import com.finfellows.domain.user.domain.Provider;
import com.finfellows.global.DefaultAssert;
import com.finfellows.global.config.company.Kakao;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(Provider.kakao.toString())) {
            System.out.println("registrationId = " + registrationId);
            Kakao kakao = new Kakao(attributes);
            return kakao;
        } else {
            DefaultAssert.isAuthentication("해당 oauth2 기능은 지원하지 않습니다.");
        }
        return null;
    }
}
