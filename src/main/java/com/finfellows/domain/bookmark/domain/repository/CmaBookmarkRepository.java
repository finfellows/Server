package com.finfellows.domain.bookmark.domain.repository;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.product.domain.CMA;
import com.finfellows.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CmaBookmarkRepository extends JpaRepository<CmaBookmark, Long> {

    List<CmaBookmark> findAllByUser(User user);
    Optional<CmaBookmark> findCmaBookmarkByCmaAndUser(CMA cma, User user);

}