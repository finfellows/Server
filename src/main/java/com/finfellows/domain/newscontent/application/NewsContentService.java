package com.finfellows.domain.newscontent.application;

import com.finfellows.domain.newscontent.domain.NewsContent;
import com.finfellows.domain.newscontent.domain.repository.NewsContentRepository;
import com.finfellows.domain.newscontent.dto.request.NewsContentRequest;
import com.finfellows.domain.newscontent.dto.response.NewsContentResponse;
import com.finfellows.domain.post.domain.Post;
import com.finfellows.domain.post.domain.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsContentService {
    private final NewsContentRepository newsContentRepository;
    private final PostRepository postRepository;

    @Transactional
    public NewsContent createNewsContent(NewsContentResponse request) {
        Post post = new Post();
        postRepository.save(post);

        // 빌더 패턴을 사용하여 NewsContent 생성하면서 Post 엔터티를 설정
        NewsContent newsContent = NewsContent.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .post(post)  // Post 엔터티를 설정
                .build();

        // NewsContent 저장
        NewsContent savedContent = newsContentRepository.save(newsContent);
        return savedContent;
    }

    public List<NewsContentResponse> getAllNewsContents() {
        List<NewsContent> newsContents = newsContentRepository.findAll();
        return newsContents.stream()
                .map(newsContent -> NewsContentResponse.builder()
                        .id(newsContent.getId())
                        .title(newsContent.getTitle())
                        .content(newsContent.getContent())
                        .build())
                .collect(Collectors.toList());
    }

    public NewsContentResponse getNewsContent(Long id) {
        NewsContent newsContent = newsContentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NewsContent not found with id: " + id));

        return NewsContentResponse.builder()
                .id(newsContent.getId())
                .title(newsContent.getTitle())
                .content(newsContent.getContent())
                .build();
    }

    @Transactional
    public void deleteNewsContent(Long id) {
        NewsContent newsContent = newsContentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NewsContent not found with id: " + id));

        newsContentRepository.delete(newsContent);
    }

    @Transactional
    public NewsContentResponse updateNewsContent(Long id, NewsContentRequest request) {
        NewsContent newsContent = newsContentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NewsContent not found with id: " + id));

        newsContent.updateContent(request.getTitle(), request.getContent());

        NewsContent updatedContent = newsContentRepository.save(newsContent);

        return NewsContentResponse.builder()
                .id(updatedContent.getId())
                .title(updatedContent.getTitle())
                .content(updatedContent.getContent())
                .build();
    }
}
