package com.finfellows.domain.chatgpt.application;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    // 라이브러리 제공
    private final ChatgptService chatgptService;

    @Value("${chatgpt.api-key}")
    private String apiKey;
//    private final ObjectMapper objectMapper = new ObjectMapper()
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE );
//    public Flux<String> ask(ChatgptQuestionRequest chatGptQuestionRequest) throws JsonProcessingException {
//        WebClient client = WebClient.builder()
//                .baseUrl(ChatgptConfig.CHAT_URL)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .defaultHeader(ChatgptConfig.AUTHORIZATION, ChatgptConfig.BEARER + apiKey)
//                .build();
//
//        List<ChatGptMessage> messages = new ArrayList<>();
//        messages.add(ChatGptMessage.builder()
//                .role(ChatgptConfig.ROLE)
//                .content(chatGptQuestionRequest.getQuestion())
//                .build());
//
//        ChatgptRequest chatGptRequest = new ChatgptRequest(
//                ChatgptConfig.CHAT_MODEL,
//                ChatgptConfig.MAX_TOKEN,
//                ChatgptConfig.TEMPERATURE,
//                ChatgptConfig.STREAM_TRUE,
//                messages
//        );
//
//        String requestValue = objectMapper.writeValueAsString(chatGptRequest);
//
//        Flux<String> eventStream = client.post()
//                .bodyValue(requestValue)
//                .accept(MediaType.TEXT_EVENT_STREAM)
//                .retrieve()
//                .bodyToFlux(String.class);
//
//        return eventStream;
//    }

    // 단답 답변
    public String getChatResponse(String prompt) {
        System.out.print(apiKey);
        // ChatGPT에 질문 전송
        return chatgptService.sendMessage(prompt);
    }
}

