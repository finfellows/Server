package com.finfellows.domain.educontent.domain.repository;

import com.finfellows.domain.educontent.domain.EduContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EduContentRepository extends JpaRepository<EduContent, Long> {
}
