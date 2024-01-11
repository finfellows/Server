package com.finfellows.domain.comment.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.finfellows.domain.chatgpt.domain.ChatGptMessage;
import com.finfellows.domain.chatgpt.config.ChatgptConfig;
import com.finfellows.domain.chatgpt.dto.request.ChatgptRequest;
import com.finfellows.domain.comment.domain.Comment;
import com.finfellows.domain.comment.domain.repository.CommentRepository;
import com.finfellows.domain.comment.dto.response.CommentListResponse;
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

import static com.finfellows.domain.user.domain.QUser.user;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
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
    public String getResponse(HttpEntity<ChatgptRequest> chatGptRequestHttpEntity) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60 * 1000);
        restTemplate.setRequestFactory(requestFactory);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                ChatgptConfig.CHAT_URL,
                chatGptRequestHttpEntity,
                String.class);

        String responseBody = responseEntity.getBody();

        return responseBody;
    }

    public String getChatResponse(String question) {
        try {
            String prompt_guide=
                    "너는 지금 청년들의 금융 지식을 향상시켜주기 위한 챗봇이야. 너의 이름은 '금토리'야. 너는 캐릭터의 역할이기 때문에 텍스트 형식으로 답변을 해야 해. 언어는 한국어로 말해야 하고, 말투는 친구한테 말하는 것처럼 반발로 해." +
                            "그리고 금융, 투자, 자산, 저축, 은행, 돈에 관련된 답변만 해야 하고, 만약 금융과 관련이 없는 질문이면 '미안해. 금융과 관련되지 않은 질문은 답변해줄 수 없어.'라고 말하면 돼. " +
                            "질문은 다음과 같아. 실제로 사용자와 대화하듯이 말해야 하고, 바로 질문에 대한 답을 해. 상식적으로 알 수도 있다는 말은 하지 마." +
                            "'네'라는 대답은 하지마. 인사말도 하지 마. 그리고 최대한 자세하게 답변해. 다시 한 번 말하지만, 반말로 말해. 그리고 문장은 끝까지 완전한 형태로 말 해. 답변 길이는 3줄 분량보다 넘지 마.";
            question=prompt_guide.concat(question);

            List<ChatGptMessage> messages = new ArrayList<>();
            messages.add(ChatGptMessage.builder()
                    .role(ChatgptConfig.ROLE)
                    .content(question)
                    .build());

            ChatgptRequest chatRequest = ChatgptRequest.builder()
                    .model(ChatgptConfig.CHAT_MODEL)
                    .maxTokens(ChatgptConfig.MAX_TOKEN)
                    .temperature(ChatgptConfig.TEMPERATURE)
                    .stream(ChatgptConfig.STREAM_FALSE)
                    .messages(messages)
                    .build();

            HttpEntity<ChatgptRequest> httpEntity = buildHttpEntity(chatRequest);

            String response = getResponse(httpEntity);
            String content = extractContentFromJson(response);
            return content;

        } catch (Exception e) {
            e.printStackTrace();
            return "request error";
        }
    }

    // JSON에서 "content" 부분 추출하는 메소드
    private String extractContentFromJson(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            if (jsonNode.has("choices") && jsonNode.get("choices").isArray()) {
                JsonNode firstChoice = jsonNode.get("choices").get(0);
                if (firstChoice.has("message") && firstChoice.get("message").has("content")) {
                    return firstChoice.get("message").get("content").asText();
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println("JSON 파싱 실패");
        }
        return jsonResponse;
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
            if (jsonNode.has("question")) {
                return jsonNode.get("question").asText();
            }
        } catch (JsonProcessingException e) {
            System.out.print("텍스트 변환 실패");
        }
        return json;
    }

    public List<CommentListResponse> getAllComments(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

        List<Comment> comments = commentRepository.findAllByUserId(userId);

        return comments.stream()
                .map(comment -> CommentListResponse.builder()
                        .commentId(comment.getCommentId())
                        .created_at(comment.getCreatedAt())
                        .greeting(comment.getGreeting())
                        .question(comment.getQuestion())
                        .answer(comment.getAnswer())
                        .userId(comment.getUser().getId())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<CommentListResponse> getGreeting() {
        return Collections.singletonList(
                CommentListResponse.builder()
                        .greeting("안녕! 나는 금토리야. 도움이 필요하다면 편하게 말해줘.")
                        .build()
        );
    }

    public void saveGreeting(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        List<Comment> comments = commentRepository.findAllByUserId(userId);

        Comment greet = Comment.builder()
                .greeting("안녕! 나는 금토리야. 도움이 필요하다면 편하게 말해줘.")
                .user(user)
                .build();
        commentRepository.save(greet);
    }
}
