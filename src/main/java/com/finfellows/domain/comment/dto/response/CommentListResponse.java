package com.finfellows.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentListResponse {
    private Long commentId;
    private LocalDateTime created_at;
    private String greeting;
    private String question;
    private String answer;
    private Long userId;
}
