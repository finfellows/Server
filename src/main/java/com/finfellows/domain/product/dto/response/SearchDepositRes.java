package com.finfellows.domain.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SearchDepositRes {

    private Long id;
    private String productName;
    private String bankName;
    private String maxInterestRate;
    private String interestRate;

    @QueryProjection
    public SearchDepositRes(Long id, String productName, String bankName, String maxInterestRate, String interestRate) {
        this.id = id;
        this.productName = productName;
        this.bankName = bankName;
        this.maxInterestRate = maxInterestRate;
        this.interestRate = interestRate;
    }

}
