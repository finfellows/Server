package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FinancialProductBookmarkRes {
    private FinancialProductType financialProductType;
    private String companyName;
    private String productName;
    private Integer maxLimit;
    private String maturityInterestRate;

    @Builder
    public FinancialProductBookmarkRes(FinancialProductType financialProductType, String companyName, String productName, Integer maxLimit, String maturityInterestRate) {
        this.financialProductType = financialProductType;
        this.companyName = companyName;
        this.productName = productName;
        this.maxLimit = maxLimit;
        this.maturityInterestRate = maturityInterestRate;
    }








    public static List<FinancialProductBookmarkRes> toDto(List<FinancialProductBookmark> bookmarks) {
        return bookmarks.stream()
                .map(bookmark -> FinancialProductBookmarkRes.builder()
                        .companyName(bookmark.getFinancialProduct().getCompanyName())
                        .productName(bookmark.getFinancialProduct().getProductName())
                        .maturityInterestRate(bookmark.getFinancialProduct().getMaturityInterestRate())
                        .maxLimit(bookmark.getFinancialProduct().getMaxLimit())
                        .build())
                .collect(Collectors.toList());

    }
}
