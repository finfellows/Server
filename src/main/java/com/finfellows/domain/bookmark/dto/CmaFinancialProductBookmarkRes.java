package com.finfellows.domain.bookmark.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CmaFinancialProductBookmarkRes {
    private List<FinancialProductBookmarkRes> financialProductBookmarks;
    private List<CmaBookmarkRes> cmaBookmarks;


    @Builder
    public CmaFinancialProductBookmarkRes(List<FinancialProductBookmarkRes> financialProductBookmarks, List<CmaBookmarkRes> cmaBookmarks) {
        this.financialProductBookmarks = financialProductBookmarks;
        this.cmaBookmarks = cmaBookmarks;
    }
}