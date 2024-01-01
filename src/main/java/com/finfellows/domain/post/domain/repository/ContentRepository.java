package com.finfellows.domain.post.domain.repository;

import com.finfellows.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Post,Long> {
}
