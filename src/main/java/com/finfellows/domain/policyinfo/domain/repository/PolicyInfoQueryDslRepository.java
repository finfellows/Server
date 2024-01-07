package com.finfellows.domain.policyinfo.domain.repository;

import com.finfellows.domain.policyinfo.dto.PolicyInfoDetailRes;
import com.finfellows.domain.policyinfo.dto.SearchPolicyInfoRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PolicyInfoQueryDslRepository {

    Page<SearchPolicyInfoRes> findPolicyInfos(String searchKeyword, Pageable pageable, Long userId);
    PolicyInfoDetailRes findPolicyDetail(Long policyId, Long userId);
}
