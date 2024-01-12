package com.finfellows.domain.product.domain;

import com.finfellows.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Bank")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Bank extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String bankName;

    private String bankCode;

    private String bankLogoUrl;

    private String bankUrl;

    private String bankTel;

    @Builder
    public Bank(String bankName, String bankCode, String bankLogoUrl, String bankUrl, String bankTel) {
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankLogoUrl = bankLogoUrl;
        this.bankUrl = bankUrl;
        this.bankTel = bankTel;
    }

}
