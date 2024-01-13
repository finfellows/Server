package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.PolicyInfoBookmark;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PolicyInfoBookmarkRes {
    private Long policyInfoId;
    private String contentName;
    private String content;


    @Builder
    public PolicyInfoBookmarkRes(Long policyInfoId, String contentName, String content) {
        this.policyInfoId = policyInfoId;
        this.contentName = contentName;
        this.content = content;
    }



    public static List<PolicyInfoBookmarkRes> toDto(List<PolicyInfoBookmark> bookmarks) {
        return bookmarks.stream()
                .map(bookmark -> PolicyInfoBookmarkRes.builder()
                        .policyInfoId(bookmark.getPolicyInfo().getId())
                        .contentName(bookmark.getPolicyInfo().getPolyBizSjNm())
                        .content(bookmark.getPolicyInfo().getPolyItcnCn())
                        .build())
                .collect(Collectors.toList());
    }
}
