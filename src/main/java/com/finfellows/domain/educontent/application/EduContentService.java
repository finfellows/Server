package com.finfellows.domain.educontent.application;

import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.educontent.domain.repository.EduContentRepository;
import com.finfellows.domain.educontent.dto.response.EduContentResponse;
import com.finfellows.domain.post.domain.Post;
import com.finfellows.domain.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EduContentService {
    private final EduContentRepository eduContentRepository;
    private final PostRepository postRepository;
    public EduContent createEduContent(EduContentResponse request) {
        Post post = new Post();
        postRepository.save(post);

        // 빌더 패턴을 사용하여 EduContent 생성하면서 Post 엔터티를 설정
        EduContent eduContent = EduContent.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .post(post)  // Post 엔터티를 설정
                .build();

        // EduContent 저장
        EduContent savedContent = eduContentRepository.save(eduContent);
        return savedContent;
    }

}
