package com.finfellows.domain.bookmark.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BookmarkRes {
    private int id;


    @Builder
    public BookmarkRes(int id) {
        this.id = id;
    }
}
