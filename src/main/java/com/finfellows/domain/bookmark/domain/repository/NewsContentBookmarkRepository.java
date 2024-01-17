package com.finfellows.domain.bookmark.domain.repository;

import com.finfellows.domain.bookmark.domain.NewsContentBookmark;
import com.finfellows.domain.newscontent.domain.NewsContent;
import com.finfellows.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NewsContentBookmarkRepository extends JpaRepository<NewsContentBookmark, Long> {
    Optional<NewsContentBookmark> findByUserAndNewsContent(User user, NewsContent newsContent);

    List<NewsContentBookmark> findAllByUser(User user);


    boolean existsByUser_IdAndNewsContent_Id(Long userId, Long newsContentId);
}
