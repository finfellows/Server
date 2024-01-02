package com.finfellows.domain.bookmark.domain.repository;

import com.finfellows.domain.bookmark.domain.EduContentBookmark;
import com.finfellows.domain.bookmark.domain.PolicyInfoBookmark;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.policyinfo.domain.PolicyInfo;
import com.finfellows.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PolicyInfoBookmarkRepository extends JpaRepository<PolicyInfoBookmark, Long> {
    Optional<PolicyInfoBookmark> findByUserAndPolicyInfo(User user, PolicyInfo policyInfo);
}
