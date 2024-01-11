package com.finfellows.domain.comment.presentation;

import com.finfellows.domain.comment.application.CommentService;
import com.finfellows.domain.comment.dto.response.CommentListResponse;
import com.finfellows.domain.comment.dto.response.CommentResponse;
import com.finfellows.global.config.security.token.CurrentUser;
import com.finfellows.global.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
@Tag(name="Chatbot",description = "Chatbot API")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;

    }

    @Operation(summary = "챗봇 질의응답 저장", description = "챗봇 질문과 답변을 저장합니다.")
    @ApiResponse(responseCode = "200", description = "챗봇 질문에 대한 답변 응답 성공", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentListResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CommentResponse> getChatResponse(
            @CurrentUser UserPrincipal userPrincipal,
            @RequestBody String question) {
        String answer = commentService.getChatResponse(question);

        if (userPrincipal == null) {
            // 로그인하지 않은 사용자인 경우
            return ResponseEntity.ok(CommentResponse.builder().answer(answer).build());
        } else {
            // 로그인한 사용자인 경우
            commentService.saveComment(userPrincipal.getId(), question, answer);
            return ResponseEntity.ok(CommentResponse.builder().answer(answer).build());
        }
    }

    @Operation(summary = "챗봇 대화 내용 조회", description = "챗봇 대화 전체 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "챗봇 대화 목록 조회 성공", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentListResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<CommentListResponse>> getAllComments(
            @CurrentUser UserPrincipal userPrincipal) {
        List<CommentListResponse> responseList;

        if (userPrincipal == null) {
            // 로그인하지 않은 사용자인 경우
            responseList = commentService.getGreeting();
        } else {
            // 로그인한 사용자인 경우
            Long userId=userPrincipal.getId();
            commentService.saveGreeting(userId);
            responseList = commentService.getAllComments(userId);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
