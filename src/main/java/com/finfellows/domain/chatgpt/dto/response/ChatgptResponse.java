package com.finfellows.domain.chatgpt.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Getter
@NoArgsConstructor
//GPT 답변에 대한 DTO
public class ChatgptResponse {
    private String id;
    private String model;
    private List<String> choices;

    @Getter
    @Setter
    public static class Usage {
        private int totalTokens;
    }
}
