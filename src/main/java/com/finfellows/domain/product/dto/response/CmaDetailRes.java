package com.finfellows.domain.product.dto.response;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.product.domain.CMA;
import com.finfellows.domain.product.domain.CmaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
@Builder
public class CmaDetailRes {

    private Boolean isLiked;
    private String productName;
    private String cmaType;
    private String bankName;
    private String bankLogoUrl;
    private String bankHomepageUrl;
    private String maturityInterestRate;
    private String specialCondition;
    private String joinWay;
    private String depositProtection;
    private String etcNote;
    private String productUrl;

    public static CmaDetailRes toDto(final CMA cma, final Optional<CmaBookmark> bookmark, String bankLogoUrl, String bankHomepageUrl) {
        return CmaDetailRes.builder()
                .isLiked(bookmark.isPresent())
                .productName(cma.getProductName())
                .cmaType(cma.getCmaType())
                .bankName(cma.getCompanyName())
                .bankLogoUrl(bankLogoUrl)
                .bankHomepageUrl(bankHomepageUrl)
                .maturityInterestRate(cma.getMaturityInterestRate())
                .specialCondition(cma.getSpecialCondition())
                .joinWay(cma.getJoinWay())
                .depositProtection(cma.getDepositProtection())
                .etcNote(cma.getEtcNote())
                .productUrl(cma.getProductUrl())
                .build();
    }

}
