package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductOption;
import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.domain.repository.BankRepository;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FinancialProductBookmarkRes {
    private Boolean isLiked;
    private Long financialProductId;
    private FinancialProductType financialProductType;
    private String companyName;
    private String productName;
    private String interestRate;
    private String maximumPreferredInterestRate;
    private String bankLogoUrl;



    @Builder
    public FinancialProductBookmarkRes(Boolean isLiked, Long financialProductId, FinancialProductType financialProductType, String companyName, String productName, String interestRate, String maximumPreferredInterestRate, String bankLogoUrl) {
        this.isLiked = isLiked;
        this.financialProductId = financialProductId;
        this.financialProductType = financialProductType;
        this.companyName = companyName;
        this.productName = productName;
        this.interestRate = interestRate;
        this.maximumPreferredInterestRate = maximumPreferredInterestRate;
        this.bankLogoUrl = bankLogoUrl;
    }





    public static List<FinancialProductBookmarkRes> toDto(List<FinancialProductBookmark> bookmarks, BankRepository bankRepository) {
        List<FinancialProductBookmarkRes> results = new ArrayList<>();

        for (FinancialProductBookmark bookmark : bookmarks) {
            FinancialProduct financialProduct = bookmark.getFinancialProduct();

            for (FinancialProductOption financialProductOption : financialProduct.getFinancialProductOption()) {
                String bankName = financialProduct.getBankName();
                String bankLogoUrl = bankRepository.findByBankName(bankName) != null ? bankRepository.findByBankName(bankName).getBankLogoUrl() : null;

                results.add(FinancialProductBookmarkRes.builder()
                        .isLiked(Boolean.TRUE)
                        .financialProductId(financialProduct.getId())
                        .financialProductType(financialProduct.getFinancialProductType())
                        .companyName(bankName)
                        .productName(financialProduct.getProductName())
                        .interestRate(financialProductOption.getInterestRate())
                        .maximumPreferredInterestRate(financialProductOption.getMaximumPreferredInterestRate())
                        .bankLogoUrl(bankLogoUrl)
                        .build());

            }
        }


        return results;

    }


}
