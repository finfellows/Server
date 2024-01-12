package com.finfellows.domain.post.application;

import com.finfellows.domain.post.domain.Content;
import com.finfellows.domain.post.domain.Post;
import com.finfellows.domain.post.domain.repository.ContentRepository;
import com.finfellows.domain.post.domain.repository.PostRepository;
import com.finfellows.domain.post.dto.request.ContentRequest;
import com.finfellows.domain.post.dto.response.ContentResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {
    private final ContentRepository contentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Content createContent(ContentRequest request) {
        Post post = new Post();
        postRepository.save(post);

        // 빌더 패턴을 사용하여 Content 생성하면서 Post 엔터티를 설정
        Content content = Content.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .post_id(post)  // Post 엔터티를 설정
                .build();

        // Content 저장
        Content savedContent = contentRepository.save(content);
        return savedContent;
    }

    public Page<ContentResponse> getAllContents(Pageable pageable) {
        Page<Content> contentsPage=contentRepository.findAll(pageable);
        List<ContentResponse> contentsResponses = contentsPage.getContent().stream()
                .map(content -> ContentResponse.builder()
                        .id(content.getId())
                        .created_at(content.getPost_id().getCreatedAt()) // 수정된 부분
                        .title(content.getTitle())
                        .content(content.getContent())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(contentsResponses, pageable, contentsPage.getTotalElements());
    }

    public ContentResponse getContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Content not found with id: " + id));

        return ContentResponse.builder()
                .id(content.getId())
                .created_at(content.getPost_id().getCreatedAt())
                .title(content.getTitle())
                .content(content.getContent())
                .build();
    }

    @Transactional
    public void deleteContent(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Content not found with id: " + id));

        contentRepository.delete(content);
    }

    @Transactional
    public ContentResponse updateContent(Long id, ContentRequest request) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Content not found with id: " + id));

        content.updateContent(request.getTitle(), request.getContent());

        Content updatedContent = contentRepository.save(content);

        return ContentResponse.builder()
                .id(updatedContent.getId())
                .title(updatedContent.getTitle())
                .content(updatedContent.getContent())
                .build();
    }
}
