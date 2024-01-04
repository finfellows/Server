package com.finfellows.domain.chatgpt.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class ChatgptQuestionRequest implements Serializable {
    private String question;
}
