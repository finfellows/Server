package com.finfellows.domain.policyinfo.application;

import com.finfellows.domain.policyinfo.domain.repository.PolicyInfoRepository;
import com.finfellows.domain.policyinfo.dto.SearchPolicyInfoRes;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PolicyInfoServiceImpl implements PolicyInfoService {

    private final PolicyInfoRepository policyInfoRepository;

    @Override
    public PagedResponse<SearchPolicyInfoRes> findPolicyInfos(UserPrincipal userPrincipal, String searchKeyword, Pageable pageable) {
        Page<SearchPolicyInfoRes> policyInfos = policyInfoRepository.findPolicyInfos(searchKeyword, pageable, userPrincipal.getId());

        return new PagedResponse<>(policyInfos);
    }

}
