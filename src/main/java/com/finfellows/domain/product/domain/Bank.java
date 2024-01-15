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

    @Column(name="bank_name")
    private String bankName;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "bank_logo_url")
    private String bankLogoUrl;

    @Column(name = "bank_url")
    private String bankUrl;

    @Column(name = "bank_tel")
    private String bankTel;

    @Column(name = "top_financial_group_no")
    private String topFinancialGroupNo;

    @Builder
    public Bank(String bankName, String bankCode, String bankLogoUrl, String bankUrl, String bankTel, String topFinancialGroupNo) {
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankLogoUrl = bankLogoUrl;
        this.bankUrl = bankUrl;
        this.bankTel = bankTel;
        this.topFinancialGroupNo = topFinancialGroupNo;
    }

}
