package com.finfellows.domain.product.dto.response;

import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductOption;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DepositDetailRes {

    private String productName;
    private String bankName;
    private String maxInterestRate;
    private String interestRate;
    private List<Integer> savingTerms;
    private Integer maxLimit;
    private String joinMember;
    private String etcNote;

    public static DepositDetailRes toDto(FinancialProduct deposit, FinancialProductOption depositOption, List<Integer> terms) {
        return DepositDetailRes.builder()
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
