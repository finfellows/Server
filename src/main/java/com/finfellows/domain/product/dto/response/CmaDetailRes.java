package com.finfellows.domain.product.dto.response;

import com.finfellows.domain.product.domain.CMA;
import com.finfellows.domain.product.domain.CmaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CmaDetailRes {

    private String productName;
    private String cmaType;
    private String bankName;
    private String maturityInterestRate;
    private String specialCondition;
    private String joinWay;
    private String depositProtection;
    private String etcNote;
    private String productUrl;

    public static CmaDetailRes toDto(final CMA cma) {
        return CmaDetailRes.builder()
                .productName(cma.getProductName())
                .cmaType(cma.getCmaType())
                .bankName(cma.getCompanyName())
                .maturityInterestRate(cma.getMaturityInterestRate())
                .specialCondition(cma.getSpecialCondition())
                .joinWay(cma.getJoinWay())
                .depositProtection(cma.getDepositProtection())
                .etcNote(cma.getEtcNote())
                .productUrl(cma.getProductUrl())
                .build();
    }

}
