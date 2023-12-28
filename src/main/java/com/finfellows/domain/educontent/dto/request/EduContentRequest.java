package com.finfellows.domain.educontent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EduContentRequest {
    private String title;
    private String content;
}
