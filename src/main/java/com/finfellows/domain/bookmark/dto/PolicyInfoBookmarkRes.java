package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.PolicyInfoBookmark;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class PolicyInfoBookmarkRes {
    private Long id;
    private String url;


    @Builder
    public PolicyInfoBookmarkRes(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public static List<PolicyInfoBookmarkRes> toDto(List<PolicyInfoBookmark> bookmarks) {
        return bookmarks.stream()
                .map(bookmark -> PolicyInfoBookmarkRes.builder()
                        .id(bookmark.getPolicyInfo().getId())
                        .url(bookmark.getPolicyInfo().getUrl())
                        .build())
                .collect(Collectors.toList());
    }
}
