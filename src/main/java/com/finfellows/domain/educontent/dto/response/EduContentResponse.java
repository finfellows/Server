package com.finfellows.domain.educontent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EduContentResponse {
    private Long id;
    private String title;
    private String content;
    private Boolean bookmarked;

    public void setBookmarked(Object bookmarked) {
        this.bookmarked = (Boolean) bookmarked;
    }
}
