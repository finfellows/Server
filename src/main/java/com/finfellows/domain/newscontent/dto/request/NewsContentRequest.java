package com.finfellows.domain.newscontent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsContentRequest {
    private LocalDateTime created_at;
    private String title;
    private String content;
}
