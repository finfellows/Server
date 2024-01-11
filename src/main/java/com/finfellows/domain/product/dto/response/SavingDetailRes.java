package com.finfellows.domain.product.dto.response;

import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@Builder
public class SavingDetailRes {

    private Boolean isLiked;
    private String productName;
    private String bankName;
    private String maxInterestRate;
    private String interestRate;
    private List<Integer> savingTerms;
    private Integer maxLimit;
    private String joinMember;
    private String etcNote;

    public static SavingDetailRes toDto(Optional<FinancialProductBookmark> bookmark, FinancialProduct deposit, FinancialProductOption depositOption, List<Integer> terms) {
        return SavingDetailRes.builder()
                .isLiked(bookmark.isPresent())
                .productName(deposit.getProductName())
                .bankName(deposit.getCompanyName())
                .maxInterestRate(depositOption.getMaximumPreferredInterestRate())
                .interestRate(depositOption.getInterestRate())
                .savingTerms(terms)
                .maxLimit(deposit.getMaxLimit())
                .joinMember(deposit.getJoinMember())
                .etcNote(deposit.getEtcNote())
                .build();
    }

}
