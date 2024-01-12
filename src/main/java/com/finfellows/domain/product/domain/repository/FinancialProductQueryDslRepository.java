package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.dto.condition.CmaSearchCondition;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.SearchBankRes;
import com.finfellows.domain.product.dto.response.SearchCmaRes;
import com.finfellows.domain.product.dto.response.SearchFinancialProductRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FinancialProductQueryDslRepository {

    Page<SearchFinancialProductRes> findFinancialProducts(FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable, FinancialProductType financialProductType);
    Page<SearchFinancialProductRes> findFinancialProductsWithAuthorization(FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable, FinancialProductType financialProductType, Long userId);
    List<SearchBankRes> findBanks(String[] bankGroupNo);
    Page<SearchCmaRes> findCmaProductsWithAuthorization(CmaSearchCondition cmaSearchCondition, Pageable pageable, Long userId);
    Page<SearchCmaRes> findCmaProducts(CmaSearchCondition cmaSearchCondition, Pageable pageable);

}