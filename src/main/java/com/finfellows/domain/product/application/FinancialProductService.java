package com.finfellows.domain.product.application;

import com.finfellows.domain.product.dto.condition.CmaSearchCondition;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.DepositDetailRes;
import com.finfellows.domain.product.dto.response.SavingDetailRes;
import com.finfellows.domain.product.dto.response.SearchCmaRes;
import com.finfellows.domain.product.dto.response.SearchFinancialProductRes;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FinancialProductService {

    PagedResponse<SearchFinancialProductRes> findDepositProducts(UserPrincipal userPrincipal, FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable);
    PagedResponse<SearchFinancialProductRes> findSavingProducts(UserPrincipal userPrincipal, FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable);
    DepositDetailRes getDepositDetail(UserPrincipal userPrincipal, Long depositId);
    SavingDetailRes getSavingDetail(UserPrincipal userPrincipal, Long savingId);
    List<String> findBanks(String bankType);
    PagedResponse<SearchCmaRes> findCmaProducts(UserPrincipal userPrincipal, CmaSearchCondition cmaSearchCondition, Pageable pageable);

}
