package com.finfellows.domain.bookmark.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class EduContentNewsContentBookmarkRes {
    List<NewsContentBookmarkRes> newsContentBookmarks;
    List<EduContentBookmarkRes> eduContentBookmarks;


    @Builder
    public EduContentNewsContentBookmarkRes(List<NewsContentBookmarkRes> newsContentBookmarks, List<EduContentBookmarkRes> eduContentBookmarks) {
        this.newsContentBookmarks = newsContentBookmarks;
        this.eduContentBookmarks = eduContentBookmarks;
    }
}
