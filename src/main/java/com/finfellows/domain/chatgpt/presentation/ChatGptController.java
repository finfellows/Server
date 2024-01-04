package com.finfellows.domain.chatgpt.presentation;

import com.finfellows.domain.chatgpt.application.ChatGptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat-gpt")
@RequiredArgsConstructor
@Slf4j
public class ChatGptController {
    private final ChatGptService chatgptService;

//    @PostMapping(value="/ask-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> ask(Locale locale,
//                            HttpServletRequest request,
//                            HttpServletResponse response,
//                            @RequestBody ChatgptQuestionRequest chatGptQuestionRequest){
//        try {
//            return chatgptService.ask(chatGptQuestionRequest);
//        }catch (JsonProcessingException je){
//            log.error(je.getMessage());
//            return Flux.empty();
//        }
//    }

    // 단답 테스트
    // https://yjkim-dev.tistory.com/56
    @PostMapping("")
    public String test(@RequestBody String question) {
        return chatgptService.getChatResponse(question);
    }
}