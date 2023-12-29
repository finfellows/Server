package com.finfellows.domain.educontent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EduContentRequest {
    private Long id;
    private String title;
    private String content;
}
