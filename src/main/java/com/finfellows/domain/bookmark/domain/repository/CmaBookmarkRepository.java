package com.finfellows.domain.bookmark.domain.repository;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmaBookmarkRepository extends JpaRepository<CmaBookmark, Long> {
}