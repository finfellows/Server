package com.finfellows.domain.product.application;

import com.finfellows.domain.product.dto.condition.DepositSearchCondition;
import com.finfellows.domain.product.dto.response.SearchDepositRes;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FinancialProductService {

    PagedResponse<SearchDepositRes> findDepositProducts(UserPrincipal userPrincipal, DepositSearchCondition depositSearchCondition, Pageable pageable);

}
