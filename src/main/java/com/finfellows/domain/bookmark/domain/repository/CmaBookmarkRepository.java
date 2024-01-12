package com.finfellows.domain.bookmark.domain.repository;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.product.domain.CMA;
import com.finfellows.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CmaBookmarkRepository extends JpaRepository<CmaBookmark, Long> {
    List<CmaBookmark> findAllByUser(User user);

    @Query("SELECT bm FROM CmaBookmark bm WHERE bm.user = :user AND bm.cma = :cma")
    Optional<CmaBookmark> findByUserAndCma(@Param("user") User user, @Param("cma") CMA cma);

    Optional<CmaBookmark> findCmaBookmarkByCmaAndUser(CMA cma, User user);
}