package com.finfellows.domain.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DepositCalculateRes {

    private Double defaultInterestCalculation;

    private Double maxInterestCalculation;

    public static DepositCalculateRes toDto(Double maxInterestRate, Double interestRate) {
        return DepositCalculateRes.builder()
                .defaultInterestCalculation(interestRate)
                .maxInterestCalculation(maxInterestRate)
                .build();
    }

}
