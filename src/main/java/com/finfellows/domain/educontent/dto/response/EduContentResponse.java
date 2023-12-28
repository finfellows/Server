package com.finfellows.domain.educontent.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EduContentResponse {
    private String title;
    private String content;
}
