package com.finfellows.domain.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SearchCmaRes {

    private Long id;
    private Boolean isLiked;
    private String productName;
    private String bankName;
    private String maturityInterestRate;

    @QueryProjection
    public SearchCmaRes(Long id, Boolean isLiked, String productName, String bankName, String maturityInterestRate) {
        this.id = id;
        this.isLiked = isLiked;
        this.productName = productName;
        this.bankName = bankName;
        this.maturityInterestRate = maturityInterestRate;
    }

}
