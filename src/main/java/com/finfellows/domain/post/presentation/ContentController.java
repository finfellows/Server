package com.finfellows.domain.post.presentation;

import com.finfellows.domain.educontent.dto.response.EduContentResponse;
import com.finfellows.domain.post.application.ContentService;
import com.finfellows.domain.post.dto.request.ContentRequest;
import com.finfellows.domain.post.dto.response.ContentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Tag(name = "Content", description = "Content API")
public class ContentController {
    private final ContentService contentService;

    @Operation(summary = "공지사항 작성", description = "공지사항을 작성합니다.")
    @ApiResponse(responseCode = "201", description = "공지사항을 작성 성공", content={
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ContentResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<com.finfellows.domain.post.domain.Content> saveContent(@RequestBody ContentRequest request) {
        com.finfellows.domain.post.domain.Content response = contentService.createContent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "공지사항 목록 조회 성공", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ContentResponse.class))
    })
    @GetMapping("")
    public ResponseEntity<List<ContentResponse>> getAllContents() {
        List<ContentResponse> responseList = contentService.getAllContents();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


    @Operation(summary = "공지사항 조회", description = "공지사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 조회 성공", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ContentResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없습니다.")
    })

    @GetMapping("/{id}")
    public ResponseEntity<ContentResponse> getContent(@PathVariable Long id) {
        ContentResponse response = contentService.getContent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "공지사항 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "공지사항 수정", description = "공지사항을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "공지사항 수정 성공", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ContentResponse.class))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ContentResponse> updateContent(@PathVariable Long id, @RequestBody ContentRequest request) {
        ContentResponse updatedContent = contentService.updateContent(id, request);
        return new ResponseEntity<>(updatedContent, HttpStatus.OK);
    }
}