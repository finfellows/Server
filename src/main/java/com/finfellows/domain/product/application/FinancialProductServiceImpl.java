package com.finfellows.domain.product.application;

import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductOption;
import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.domain.repository.FinancialProductOptionRepository;
import com.finfellows.domain.product.domain.repository.FinancialProductRepository;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.DepositDetailRes;
import com.finfellows.domain.product.dto.response.SearchFinancialProductRes;
import com.finfellows.domain.product.exception.InvalidFinancialProductException;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FinancialProductServiceImpl implements FinancialProductService {

    private final FinancialProductRepository financialProductRepository;
    private final FinancialProductOptionRepository financialProductOptionRepository;

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

    @Override
    public DepositDetailRes getDepositDetail(UserPrincipal userPrincipal, Long depositId) {
        FinancialProduct deposit = financialProductRepository.findById(depositId)
                .orElseThrow(InvalidFinancialProductException::new);

        List<FinancialProductOption> depositOptions = financialProductOptionRepository.findFinancialProductOptionsByFinancialProduct(deposit);
        List<Integer> terms = depositOptions.stream()
                .map(FinancialProductOption::getSavingsTerm)
                .distinct()
                .sorted()
                .toList();

        FinancialProductOption maxOption = depositOptions.stream()
                .max(Comparator.comparing(FinancialProductOption::getMaximumPreferredInterestRate))
                .orElse(null);

        return DepositDetailRes.toDto(deposit, maxOption, terms);
    }

}
