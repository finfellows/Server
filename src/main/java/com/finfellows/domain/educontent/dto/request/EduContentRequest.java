package com.finfellows.domain.educontent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EduContentRequest {
    private Long id;
    private LocalDateTime created_at;
    private String title;
    private String content;
}
