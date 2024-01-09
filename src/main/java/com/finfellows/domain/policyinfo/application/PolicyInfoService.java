package com.finfellows.domain.policyinfo.application;

import com.finfellows.domain.policyinfo.dto.PolicyInfoDetailRes;
import com.finfellows.domain.policyinfo.dto.PolicyUpdateReq;
import com.finfellows.domain.policyinfo.dto.SearchPolicyInfoRes;
import com.finfellows.global.config.security.token.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PolicyInfoService {

    Page<SearchPolicyInfoRes> findPolicyInfos(UserPrincipal userPrincipal, String searchKeyword, Pageable pageable);
    PolicyInfoDetailRes findPolicyDetail(UserPrincipal userPrincipal, Long policyId);
    void deletePolicy(Long policyId);
    void updatePolicy(Long policyId, PolicyUpdateReq policyUpdateReq);

}
