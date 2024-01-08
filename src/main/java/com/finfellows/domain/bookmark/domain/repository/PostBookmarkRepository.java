package com.finfellows.domain.bookmark.domain.repository;

import com.finfellows.domain.bookmark.domain.PostBookmark;
import com.finfellows.domain.post.domain.ContentType;
import com.finfellows.domain.post.domain.Post;
import com.finfellows.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
    Optional<PostBookmark> findByUserAndPost(User user, Post post);

    List<PostBookmark> findAllByUser(User user);

}
