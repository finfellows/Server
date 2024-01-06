package com.finfellows.domain.comment.presentation;

import com.finfellows.domain.chatgpt.application.ChatGptService;
import com.finfellows.domain.comment.application.CommentService;
import com.finfellows.domain.comment.dto.response.CommentResponse;
import com.finfellows.domain.user.application.UserService;
import com.finfellows.global.config.security.token.CurrentUser;
import com.finfellows.global.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
@Tag(name="Chatbot",description = "Chatbot API")
public class CommentController {
    private CommentService commentService;
    private ChatGptService chatGptService;
    private UserService userService;


    @Autowired
    public CommentController(CommentService commentService, ChatGptService chatGptService, UserService userService) {
        this.commentService = commentService;
        this.chatGptService = chatGptService;
        this.userService = userService;
    }

    @PostMapping("")
    public String getChatResponse(@CurrentUser UserPrincipal userPrincipal, @RequestBody String question){
        String answer= commentService.getChatResponse(question);
        commentService.saveComment(userPrincipal.getId(), question, answer);
        return answer;
    }
    @GetMapping("")
    public ResponseEntity<List<CommentResponse>> getAllComments(@CurrentUser UserPrincipal userPrincipal){
        List<CommentResponse> responseList=commentService.getAllComments(userPrincipal.getId());
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
