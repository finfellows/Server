package com.finfellows.domain.newscontent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsContentResponse {
    private Long id;
    private LocalDateTime created_at;
    private String title;
    private String content;
    private Boolean bookmarked;

    public void setBookmarked(Object bookmarked) {
        this.bookmarked = (Boolean) bookmarked;
    }
}
