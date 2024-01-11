package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.PostBookmark;
import com.finfellows.domain.bookmark.domain.repository.PostBookmarkRepository;
import com.finfellows.domain.bookmark.dto.PostBookmarkRes;
import com.finfellows.domain.post.domain.ContentType;
import com.finfellows.domain.post.domain.Post;
import com.finfellows.domain.post.domain.repository.PostRepository;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.Message;
import com.finfellows.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostBookmarkServiceImpl {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostBookmarkRepository postBookmarkRepository;


    @Transactional
    public Message insert(UserPrincipal userPrincipal, Long postId, ContentType contentType) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);
        Post post = postRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);


        PostBookmark postBookmark = PostBookmark.builder()
                .user(user)
                .post(post)
                .contentType(contentType)
                .build();

        postBookmarkRepository.save(postBookmark);

        return Message.builder()
                .message("즐겨찾기 추가에 성공했습니다.")
                .build();
    }

    @Transactional
    public Message delete(UserPrincipal userPrincipal, Long postId) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(RuntimeException::new);

        PostBookmark postBookmark = postBookmarkRepository.findByUserAndPost(user, post).get();

        postBookmarkRepository.delete(postBookmark);


        return Message.builder()
                .message("즐겨찾기 삭제에 성공했습니다.")
                .build();
    }

    @Transactional
    public ResponseCustom<?> findBookmarks(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);

        List<PostBookmark> bookmarks = postBookmarkRepository.findAllByUser(user);


        List<PostBookmarkRes> postBookmarkResList = PostBookmarkRes.toDto(bookmarks);

        return ResponseCustom.OK(postBookmarkResList);
    }
}
