package com.finfellows.domain.newscontent.presentation;

import com.finfellows.domain.newscontent.application.NewsContentService;
import com.finfellows.domain.newscontent.domain.NewsContent;
import com.finfellows.domain.newscontent.dto.request.NewsContentRequest;
import com.finfellows.domain.newscontent.dto.response.NewsContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/learn/news")
public class NewsContentController {
    private final NewsContentService newsContentService;

    //저장
    @PostMapping("")
    public ResponseEntity<NewsContent> saveNewsContent(@RequestBody NewsContentResponse request){
        NewsContent response = newsContentService.createNewsContent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //전체 목록 조회(title + content)
    @GetMapping
    public ResponseEntity<List<NewsContentResponse>> getAllNewsContents() {
        List<NewsContentResponse> responseList = newsContentService.getAllNewsContents();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    //상세 내용 조회
    @GetMapping("/{id}")
    public ResponseEntity<NewsContentResponse> getNewsContent(@PathVariable Long id) {
        NewsContentResponse response = newsContentService.getNewsContent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewsContent(@PathVariable Long id) {
        newsContentService.deleteNewsContent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //수정
    @PatchMapping("/{id}")
    public ResponseEntity<NewsContentResponse> updateNewsContent(@PathVariable Long id, @RequestBody NewsContentRequest request) {
        NewsContentResponse updatedContent = newsContentService.updateNewsContent(id, request);
        return new ResponseEntity<>(updatedContent, HttpStatus.OK);
    }


}
