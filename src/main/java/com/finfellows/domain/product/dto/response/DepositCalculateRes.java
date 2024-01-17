package com.finfellows.domain.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DepositCalculateRes {

    private String defaultInterestCalculation;

    private String maxInterestCalculation;

    public static DepositCalculateRes toDto(String maxInterestRate, String interestRate) {
        return DepositCalculateRes.builder()
                .defaultInterestCalculation(interestRate)
                .maxInterestCalculation(maxInterestRate)
                .build();
    }

}
