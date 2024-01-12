package com.finfellows.domain.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SearchBankRes {

    private String bankName;
    private String bankLogoUrl;

    @QueryProjection
    public SearchBankRes(String bankName, String bankLogoUrl) {
        this.bankName = bankName;
        this.bankLogoUrl = bankLogoUrl;
    }

}
