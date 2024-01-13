package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductOption;
import com.finfellows.domain.product.domain.FinancialProductType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FinancialProductBookmarkRes {
    private Long financialProductId;
    private FinancialProductType financialProductType;
    private String companyName;
    private String productName;
    private String interestRate;
    private String maximumPreferredInterestRate;


    @Builder
    public FinancialProductBookmarkRes(Long financialProductId, FinancialProductType financialProductType, String companyName, String productName, String interestRate, String maximumPreferredInterestRate) {
        this.financialProductId = financialProductId;
        this.financialProductType = financialProductType;
        this.companyName = companyName;
        this.productName = productName;
        this.interestRate = interestRate;
        this.maximumPreferredInterestRate = maximumPreferredInterestRate;
    }



    public static List<FinancialProductBookmarkRes> toDto(List<FinancialProductBookmark> bookmarks) {
        List<FinancialProductBookmarkRes> results = new ArrayList<>();

        for (FinancialProductBookmark bookmark : bookmarks) {
            FinancialProduct financialProduct = bookmark.getFinancialProduct();

            for (FinancialProductOption financialProductOption : financialProduct.getFinancialProductOption()) {
                Long financialProductId = financialProduct.getId();
                FinancialProductType financialProductType = financialProduct.getFinancialProductType();
                String companyName = financialProduct.getCompanyName();
                String productName = financialProduct.getProductName();
                String interestRate = financialProductOption.getInterestRate();
                String maximumPreferredInterestRate = financialProductOption.getMaximumPreferredInterestRate();

                results.add(new FinancialProductBookmarkRes(financialProductId ,financialProductType, companyName, productName, interestRate, maximumPreferredInterestRate));

            }
        }


        return results;

    }


}
