package com.finfellows.domain.educontent.presentation;

import com.finfellows.domain.educontent.application.EduContentService;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.educontent.dto.request.EduContentRequest;
import com.finfellows.domain.educontent.dto.response.EduContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//교육 콘텐츠 저장
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/learn/edu")
public class EduContentController {
    private final EduContentService eduContentService;


    //저장
    @PostMapping("")
    public ResponseEntity<EduContent> saveEduContent(@RequestBody EduContentResponse request){
        EduContent response = eduContentService.createEduContent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //전체 목록 조회(title + content)
    @GetMapping
    public ResponseEntity<List<EduContentResponse>> getAllEduContents() {
        List<EduContentResponse> responseList = eduContentService.getAllEduContents();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    //상세 내용 조회
    @GetMapping("/{id}")
    public ResponseEntity<EduContentResponse> getEduContent(@PathVariable Long id) {
        EduContentResponse response = eduContentService.getEduContent(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEduContent(@PathVariable Long id) {
        eduContentService.deleteEduContent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //수정
    @PatchMapping("/{id}")
    public ResponseEntity<EduContentResponse> updateEduContent(@PathVariable Long id, @RequestBody EduContentRequest request) {
        EduContentResponse updatedContent = eduContentService.updateEduContent(id, request);
        return new ResponseEntity<>(updatedContent, HttpStatus.OK);
    }





}
