package com.finfellows.domain.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SearchFinancialProductRes {

    private Long id;
    private Boolean isLiked;
    private String productName;
    private String bankName;
    private String bankLogoUrl;
    private String maxInterestRate;
    private String interestRate;

    @QueryProjection
    public SearchFinancialProductRes(Long id, Boolean isLiked, String productName, String bankName, String bankLogoUrl, String maxInterestRate, String interestRate) {
        this.id = id;
        this.isLiked = isLiked;
        this.productName = productName;
        this.bankName = bankName;
        this.bankLogoUrl = bankLogoUrl;
        this.maxInterestRate = maxInterestRate;
        this.interestRate = interestRate;
    }

}
