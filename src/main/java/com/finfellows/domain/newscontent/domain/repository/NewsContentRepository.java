package com.finfellows.domain.newscontent.domain.repository;

import com.finfellows.domain.newscontent.domain.NewsContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsContentRepository extends JpaRepository<NewsContent, Long> {
}
