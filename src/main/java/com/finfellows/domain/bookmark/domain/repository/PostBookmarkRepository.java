package com.finfellows.domain.bookmark.domain.repository;

import com.finfellows.domain.bookmark.domain.PostBookmark;
import com.finfellows.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
}
