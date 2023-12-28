package com.finfellows.domain.educontent.presentation;

import com.finfellows.domain.educontent.application.EduContentService;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.educontent.dto.response.EduContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/edu")
public class EduContentController {
    private final EduContentService eduContentService;


    @PostMapping("")
    public ResponseEntity<EduContent> saveEduContent(@RequestBody EduContentResponse request){
        EduContent response = eduContentService.createEduContent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
