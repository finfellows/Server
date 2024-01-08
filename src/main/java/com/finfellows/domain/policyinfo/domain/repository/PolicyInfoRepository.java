package com.finfellows.domain.policyinfo.domain.repository;

import com.finfellows.domain.policyinfo.domain.PolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyInfoRepository extends JpaRepository<PolicyInfo, Long> , PolicyInfoQueryDslRepository {
}
