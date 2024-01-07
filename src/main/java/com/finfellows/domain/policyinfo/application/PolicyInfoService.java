package com.finfellows.domain.policyinfo.application;

import com.finfellows.domain.policyinfo.dto.PolicyInfoDetailRes;
import com.finfellows.domain.policyinfo.dto.SearchPolicyInfoRes;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface PolicyInfoService {

    PagedResponse<SearchPolicyInfoRes> findPolicyInfos(UserPrincipal userPrincipal, String searchKeyword, Pageable pageable);
    PolicyInfoDetailRes findPolicyDetail(UserPrincipal userPrincipal, Long policyId);

}
