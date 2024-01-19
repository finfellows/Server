package com.finfellows.domain.post.domain.repository;

import com.finfellows.domain.post.domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
}
