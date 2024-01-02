package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.EduContentBookmark;
import com.finfellows.domain.educontent.domain.EduContent;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EduContentBookmarkRes {
    private Long id;
    private String content;

    @Builder
    public EduContentBookmarkRes(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static List<EduContentBookmarkRes> toDto(List<EduContentBookmark> bookmarks) {
        return bookmarks.stream()
                .map(bookmark -> EduContentBookmarkRes.builder()
                        .id(bookmark.getEduContent().getId())
                        .content(bookmark.getEduContent().getContent())
                        .build())
                .collect(Collectors.toList());
    }

}
