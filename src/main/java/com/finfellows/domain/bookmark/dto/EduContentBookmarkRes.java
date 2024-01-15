package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.EduContentBookmark;
import com.finfellows.domain.post.domain.ContentType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EduContentBookmarkRes {
    private Boolean isLiked;
    private Long eduContentId;
    private String title;
    private String content;
    private ContentType contentType;

    @Builder
    public EduContentBookmarkRes(Boolean isLiked, Long eduContentId, String title, String content, ContentType contentType) {
        this.isLiked = isLiked;
        this.eduContentId = eduContentId;
        this.title = title;
        this.content = content;
        this.contentType = contentType;
    }

    public static List<EduContentBookmarkRes> toDto(List<EduContentBookmark> eduContentBookmarks) {
        return eduContentBookmarks.stream()
                .map(bookmark -> EduContentBookmarkRes.builder()
                        .isLiked(Boolean.TRUE)
                        .eduContentId(bookmark.getEduContent().getId())
                        .title(bookmark.getEduContent().getTitle())
                        .content(bookmark.getEduContent().getContent())
                        .contentType(ContentType.EDU_CONTENT)
                        .build())
                .collect(Collectors.toList());
    }
}
