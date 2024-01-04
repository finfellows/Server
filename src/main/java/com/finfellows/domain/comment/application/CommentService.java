package com.finfellows.domain.comment.application;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.finfellows.domain.chatgpt.domain.ChatGptMessage;
import com.finfellows.domain.chatgpt.application.ChatGptService;
import com.finfellows.domain.chatgpt.config.ChatgptConfig;
import com.finfellows.domain.chatgpt.dto.request.ChatgptQuestionRequest;
import com.finfellows.domain.chatgpt.dto.request.ChatgptRequest;
import com.finfellows.domain.chatgpt.dto.response.ChatgptResponse;
import com.finfellows.domain.comment.domain.repository.CommentRepository;
import com.finfellows.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final ChatGptService chatGptService;
    private final CommentRepository commentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${chatgpt.api-key}")
    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    // gpt 단답
    public HttpEntity<ChatgptRequest> buildHttpEntity(ChatgptRequest chatGptRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(ChatgptConfig.MEDIA_TYPE));
        httpHeaders.add(ChatgptConfig.AUTHORIZATION, ChatgptConfig.BEARER + apiKey);
        return new HttpEntity<>(chatGptRequest, httpHeaders);
    }

    // gpt 단답
    public ChatgptResponse getResponse(HttpEntity<ChatgptRequest> chatGptRequestHttpEntity) {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        //답변이 길어질 경우 TimeOut Error가 발생하니 1분정도 설정해줍니다.
        requestFactory.setReadTimeout(60 * 1000);   //  1min = 60 sec * 1,000ms
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<ChatgptResponse> responseEntity = restTemplate.postForEntity(
                ChatgptConfig.CHAT_URL,
                chatGptRequestHttpEntity,
                ChatgptResponse.class);

        return responseEntity.getBody();
    }

    // gpt 단답
    public ChatgptResponse askQuestion(ChatgptQuestionRequest questionRequest) {
        List<ChatGptMessage> messages = new ArrayList<>();
        messages.add(ChatGptMessage.builder()
                .role(ChatgptConfig.ROLE)
                .content(questionRequest.getQuestion())
                .build());
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatgptRequest(
                                ChatgptConfig.CHAT_MODEL,
                                ChatgptConfig.MAX_TOKEN,
                                ChatgptConfig.TEMPERATURE,
                                ChatgptConfig.STREAM_FALSE,
                                messages
                        )
                )
        );
    }
}
