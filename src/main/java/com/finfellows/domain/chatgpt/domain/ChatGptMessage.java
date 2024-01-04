package com.finfellows.domain.chatgpt.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatGptMessage {
    private String role;
    private String content;
}
