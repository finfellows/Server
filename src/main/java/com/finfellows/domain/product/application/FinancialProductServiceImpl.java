package com.finfellows.domain.product.application;

import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.domain.repository.FinancialProductRepository;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.SearchFinancialProductRes;
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
public class FinancialProductServiceImpl implements FinancialProductService {

    private final FinancialProductRepository financialProductRepository;

    @Override
    public PagedResponse<SearchFinancialProductRes> findDepositProducts(UserPrincipal userPrincipal, FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable) {
        Page<SearchFinancialProductRes> financialProducts = financialProductRepository.findFinancialProducts(financialProductSearchCondition, pageable, FinancialProductType.DEPOSIT);

        return new PagedResponse<>(financialProducts);
    }

    @Override
    public PagedResponse<SearchFinancialProductRes> findSavingProducts(UserPrincipal userPrincipal, FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable) {
        Page<SearchFinancialProductRes> financialProducts = financialProductRepository.findFinancialProducts(financialProductSearchCondition, pageable, FinancialProductType.SAVING);

        return new PagedResponse<>(financialProducts);
    }

}
