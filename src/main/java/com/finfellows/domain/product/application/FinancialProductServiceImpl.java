package com.finfellows.domain.product.application;

import com.finfellows.domain.product.domain.repository.FinancialProductRepository;
import com.finfellows.domain.product.dto.condition.DepositSearchCondition;
import com.finfellows.domain.product.dto.response.SearchDepositRes;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FinancialProductServiceImpl implements FinancialProductService {

    private final FinancialProductRepository financialProductRepository;

    @Override
    public PagedResponse<SearchDepositRes> findDepositProducts(UserPrincipal userPrincipal, DepositSearchCondition depositSearchCondition, Pageable pageable) {
        Page<SearchDepositRes> financialProducts = financialProductRepository.findFinancialProducts(depositSearchCondition, pageable);

        return new PagedResponse<>(financialProducts);
    }

}
