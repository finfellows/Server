package com.finfellows.domain.chatgpt.presentation;

import com.finfellows.domain.chatgpt.application.ChatGptService;
import com.finfellows.domain.comment.application.CommentService;
import com.finfellows.domain.comment.domain.QComment;
import com.finfellows.global.config.security.token.CurrentUser;
import com.finfellows.global.config.security.token.UserPrincipal;
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
    private final CommentService commentService;

    // 단답 테스트
    // https://yjkim-dev.tistory.com/56
    @PostMapping("/test")
    public String test(@RequestBody String question) {
        return chatgptService.getChatResponse(question);
    }

    // 답변 저장 테스트
    @PostMapping("")
    public String getChatResponse(@CurrentUser UserPrincipal userPrincipal, @RequestBody String question){
        String answer= commentService.getChatResponse(question);
        commentService.saveComment(userPrincipal.getId(), question, answer);
        return answer;
    }

}