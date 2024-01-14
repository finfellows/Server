package com.finfellows.domain.image.presentation;

import com.finfellows.domain.image.application.ImageService;
import com.finfellows.domain.image.dto.response.ImageResponse;
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
    @PostMapping
    public ResponseEntity<ImageResponse> uploadImage(@RequestParam MultipartFile image) throws IOException {
        ImageResponse response = imageService.uploadImageToS3(image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
