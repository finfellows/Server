package com.finfellows.domain.policyinfo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class SearchPolicyInfoRes {

    private Long policyInfoId;
    private String policyName;
    private String policyContent;
    private Boolean isLiked;

    @QueryProjection
    public SearchPolicyInfoRes(Long policyInfoId, String policyName, String policyContent, Boolean isLiked) {
        this.policyInfoId = policyInfoId;
        this.policyName = policyName;
        this.policyContent = policyContent;
        this.isLiked = isLiked;
    }

}
