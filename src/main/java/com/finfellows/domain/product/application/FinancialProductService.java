package com.finfellows.domain.product.application;

import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.SearchFinancialProductRes;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.PagedResponse;
import org.springframework.data.domain.Pageable;

public interface FinancialProductService {

    PagedResponse<SearchFinancialProductRes> findDepositProducts(UserPrincipal userPrincipal, FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable);
    PagedResponse<SearchFinancialProductRes> findSavingProducts(UserPrincipal userPrincipal, FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable);

}
