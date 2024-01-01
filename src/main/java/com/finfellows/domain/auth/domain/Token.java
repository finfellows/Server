package com.finfellows.domain.auth.domain;

import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "token")
@NoArgsConstructor // 생성자 직접 생성 대신 써줬는데 문제가 된다면 삭제하고 token 생성자 직접 만들어 수정해야할듯
@Entity
public class Token extends BaseEntity {

    @Id  // 이 컬럼이 email로 저장안되고 providerId로 저장되고 있음. 11.25
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;


    public Token updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }


    @Builder
    public Token(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }
}
