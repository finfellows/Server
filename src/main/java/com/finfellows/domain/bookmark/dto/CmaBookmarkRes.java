package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.product.domain.Bank;
import com.finfellows.domain.product.domain.repository.BankRepository;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CmaBookmarkRes {
    private Boolean isLiked;
    private Long cmaId;
    private String companyName;
    private String productName;
    private String cmaType;
    private String maturityInterestRate;
    private String specialCondition;
    private String bankLogoUrl;




    @Builder
    public CmaBookmarkRes(Boolean isLiked, Long cmaId, String companyName, String productName, String cmaType, String maturityInterestRate, String specialCondition, String bankLogoUrl) {
        this.isLiked = isLiked;
        this.cmaId = cmaId;
        this.companyName = companyName;
        this.productName = productName;
        this.cmaType = cmaType;
        this.maturityInterestRate = maturityInterestRate;
        this.specialCondition = specialCondition;
        this.bankLogoUrl = bankLogoUrl;
    }





    public static List<CmaBookmarkRes> toDto(List<CmaBookmark> cmaBookmarks, BankRepository bankRepository) {

        return cmaBookmarks.stream()
                .map(bookmark -> {
                    Bank bank = bankRepository.findByBankName(bookmark.getCma().getBankName());
                    String bankLogoUrl = bank != null ? bank.getBankLogoUrl() : null;

                    return CmaBookmarkRes.builder()
                            .isLiked(Boolean.TRUE)
                            .cmaId(bookmark.getCma().getId())
                            .cmaType(bookmark.getCma().getCmaType())
                            .productName(bookmark.getCma().getProductName())
                            .companyName(bookmark.getCma().getBankName())
                            .maturityInterestRate(bookmark.getCma().getMaturityInterestRate())
                            .specialCondition(bookmark.getCma().getSpecialCondition())
                            .bankLogoUrl(bankLogoUrl)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
