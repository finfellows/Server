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

            // financialProductId가 이미 포함되어 있으면 건너뛴다
            if (!includedFinancialProductIds.contains(financialProductId)) {
                String bankName = financialProduct.getBankName();
                String bankLogoUrl = bankRepository.findByBankName(bankName) != null ? bankRepository.findByBankName(bankName).getBankLogoUrl() : null;

                // 대표 옵션 선택 또는 모든 옵션 정보 집계
                FinancialProductOption representativeOption = financialProduct.getFinancialProductOption().get(0); // 예시: 첫 번째 옵션 사용

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

                // financialProductId를 포함된 목록에 추가한다
                includedFinancialProductIds.add(financialProductId);
            }
        }

        return results;

    }


}
