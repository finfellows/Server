package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.product.domain.FinancialProduct;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FinancialProductBookmarkRes {
    private String productName;
    private String companyName;
    private String maturityInterestRate;
    private String specialCondition;
    private Integer maxLimit;
    private String joinWay;

    @Builder
    public FinancialProductBookmarkRes(String productName, String companyName, String maturityInterestRate, String specialCondition, Integer maxLimit, String joinWay) {
        this.productName = productName;
        this.companyName = companyName;
        this.maturityInterestRate = maturityInterestRate;
        this.specialCondition = specialCondition;
        this.maxLimit = maxLimit;
        this.joinWay = joinWay;
    }





    public static List<FinancialProductBookmarkRes> toDto(List<FinancialProductBookmark> bookmarks) {
        return bookmarks.stream()
                .map(bookmark -> FinancialProductBookmarkRes.builder()
                        .companyName(bookmark.getFinancialProduct().getCompanyName())
                        .productName(bookmark.getFinancialProduct().getProductName())
                        .joinWay(bookmark.getFinancialProduct().getJoinWay())
                        .maturityInterestRate(bookmark.getFinancialProduct().getMaturityInterestRate())
                        .specialCondition(bookmark.getFinancialProduct().getSpecialCondition())
                        .maxLimit(bookmark.getFinancialProduct().getMaxLimit())
                        .build())
                .collect(Collectors.toList());

    }
}
