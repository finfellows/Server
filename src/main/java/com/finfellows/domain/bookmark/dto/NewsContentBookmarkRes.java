package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.NewsContentBookmark;
import com.finfellows.domain.post.domain.ContentType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class NewsContentBookmarkRes {
    private Boolean isLiked;
    private Long newsContentId;
    private String title;
    private String content;
    private ContentType contentType;


    @Builder
    public NewsContentBookmarkRes(Boolean isLiked, Long newsContentId, String title, String content, ContentType contentType) {
        this.isLiked = isLiked;
        this.newsContentId = newsContentId;
        this.title = title;
        this.content = content;
        this.contentType = contentType;
    }

    public static List<NewsContentBookmarkRes> toDto(List<NewsContentBookmark> newsContentBookmarks) {
        return newsContentBookmarks.stream()
                .map(bookmark -> NewsContentBookmarkRes.builder()
                        .isLiked(Boolean.TRUE)
                        .newsContentId(bookmark.getNewsContent().getId())
                        .title(bookmark.getNewsContent().getTitle())
                        .content(bookmark.getNewsContent().getContent())
                        .contentType(ContentType.NEWS_CONTENT)
                        .build())
                .collect(Collectors.toList());
    }
}
