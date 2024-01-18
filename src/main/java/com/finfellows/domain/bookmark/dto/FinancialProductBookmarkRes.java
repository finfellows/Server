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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Set<Long> includedFinancialProductIds = new HashSet<>();

        for (FinancialProductBookmark bookmark : bookmarks) {
            FinancialProduct financialProduct = bookmark.getFinancialProduct();
            Long financialProductId = financialProduct.getId();

            // Skip if financialProductId is already included
            if (!includedFinancialProductIds.contains(financialProductId)) {
                // Check if any of the FinancialProductOptions have a savingTerm of 12
                boolean hasSavingTerm12 = financialProduct.getFinancialProductOption().stream()
                        .anyMatch(option -> option.getSavingsTerm() != null && option.getSavingsTerm().equals(12));

                if (hasSavingTerm12) {
                    String bankName = financialProduct.getBankName();
                    String bankLogoUrl = bankRepository.findByBankName(bankName) != null ? bankRepository.findByBankName(bankName).getBankLogoUrl() : null;

                    // Use the first option as the representative option
                    FinancialProductOption representativeOption = financialProduct.getFinancialProductOption().get(0);

                    results.add(FinancialProductBookmarkRes.builder()
                            .isLiked(Boolean.TRUE)
                            .financialProductId(financialProductId)
                            .financialProductType(financialProduct.getFinancialProductType())
                            .companyName(bankName)
                            .productName(financialProduct.getProductName())
                            .interestRate(representativeOption.getInterestRate())
                            .maximumPreferredInterestRate(representativeOption.getMaximumPreferredInterestRate())
                            .bankLogoUrl(bankLogoUrl)
                            .build());

                    // Add the financialProductId to the included set
                    includedFinancialProductIds.add(financialProductId);
                }
            }
        }

        return results;

    }


}
