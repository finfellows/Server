package com.finfellows.domain.image.presentation;

import com.finfellows.domain.comment.dto.response.CommentListResponse;
import com.finfellows.domain.image.application.ImageService;
import com.finfellows.domain.image.dto.response.ImageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageControlller {
    private final ImageService imageService;
    @Operation(summary = "이미지 업로드", description = "S3 버킷에 이미지를 업로드 합니다. 파라미터로 image 파일을 요청하고, url 주소로 응답합니다. ")
    @ApiResponse(responseCode = "200", description = "이미지 업로드 및 url 변환 성공", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ImageResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image) throws IOException {
        ImageResponse response = imageService.uploadImageToS3(image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
