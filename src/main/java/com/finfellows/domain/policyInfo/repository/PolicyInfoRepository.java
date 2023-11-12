package com.finfellows.domain.policyInfo.repository;

import com.finfellows.domain.policyInfo.PolicyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyInfoRepository extends JpaRepository<PolicyInfo, Long> {
}
