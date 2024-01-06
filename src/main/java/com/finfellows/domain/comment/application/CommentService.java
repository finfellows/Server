package com.finfellows.domain.comment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.finfellows.domain.chatgpt.domain.ChatGptMessage;
import com.finfellows.domain.chatgpt.application.ChatGptService;
import com.finfellows.domain.chatgpt.config.ChatgptConfig;
import com.finfellows.domain.chatgpt.dto.request.ChatgptQuestionRequest;
import com.finfellows.domain.chatgpt.dto.request.ChatgptRequest;
import com.finfellows.domain.chatgpt.dto.response.ChatgptResponse;
import com.finfellows.domain.comment.domain.Comment;
import com.finfellows.domain.comment.domain.repository.CommentRepository;
import com.finfellows.domain.comment.dto.response.CommentResponse;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

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

    public String getChatResponse(String question){
        String responseFromGPT=chatGptService.getChatResponse(question);
        return responseFromGPT;
    }

    public void saveComment(Long userId, String question, String answer) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        question = extractPromptFromJson(question);
        answer = answer.replaceAll("\\n", "");
        answer = answer.replaceAll("금토리: ", "");
        answer = answer.replaceAll("네. ", "");

        Comment comment = Comment.builder()
                .question(question)
                .answer(answer)
                .user(user)
                .build();
        commentRepository.save(comment);
    }

    // JSON에서 "prompt" 부분 추출하는 메소드
    private String extractPromptFromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            if (jsonNode.has("prompt")) {
                return jsonNode.get("prompt").asText();
            }
        } catch (JsonProcessingException e) {
            System.out.print("텍스트 변환 실패");
        }
        return json;
    }

    public List<CommentResponse> getAllComments(Long userId) {
        List<Comment> comments = commentRepository.findAllByUserId(userId);

        Comment greet = Comment.builder()
                .greeting("안녕! 나는 금토리야. 도움이 필요하다면 편하게 말해줘.")
                .build();
        commentRepository.save(greet);

        return comments.stream()
                .map(comment -> CommentResponse.builder()
                        .commentId(comment.getCommentId())
                        .created_at(comment.getCreatedAt())
                        .greeting(comment.getGreeting())
                        .question(comment.getQuestion())
                        .answer(comment.getAnswer())
                        .userId(comment.getUser())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
