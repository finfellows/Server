package com.finfellows.domain.newscontent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsContentRequest {
    private Long id;
    private String title;
    private String content;
}
