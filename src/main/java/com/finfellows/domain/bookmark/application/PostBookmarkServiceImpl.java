package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.repository.PostBookmarkRepository;
import com.finfellows.domain.post.domain.ContentType;
import com.finfellows.domain.post.domain.repository.PostRepository;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostBookmarkServiceImpl {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostBookmarkRepository postBookmarkRepository;


    @Transactional
    public Message insert(UserPrincipal userPrincipal, Long postId, ContentType contentType) {
        return null;
    }


    public Message delete(UserPrincipal userPrincipal, Long postId, ContentType contentType) {
        return null;
    }
}
