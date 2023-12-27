package com.finfellows.domain.chatgpt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finfellows.domain.chatgpt.domain.ChatGptMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Getter
@NoArgsConstructor
//chatGPT에 요청할 DTO Format
public class ChatgptRequest implements Serializable {
    private String model;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    private Double temperature;
    private Boolean stream;
    private List<ChatGptMessage> messages;

    //@JsonProperty("top_p")
    //private Double topP;

    @Builder
    public ChatgptRequest(String model, Integer maxTokens, Double temperature,
                          Boolean stream, List<ChatGptMessage> messages
            /*,Double topP*/) {
        this.model = model;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.stream = stream;
        this.messages = messages;
        //this.topP = topP;
    }
}
