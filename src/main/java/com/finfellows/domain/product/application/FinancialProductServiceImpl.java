package com.finfellows.domain.product.application;

import com.finfellows.domain.bookmark.domain.repository.FinancialProductBookmarkRepository;
import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductOption;
import com.finfellows.domain.product.domain.FinancialProductType;
import com.finfellows.domain.product.domain.repository.FinancialProductOptionRepository;
import com.finfellows.domain.product.domain.repository.FinancialProductRepository;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.response.DepositDetailRes;
import com.finfellows.domain.product.dto.response.SavingDetailRes;
import com.finfellows.domain.product.dto.response.SearchFinancialProductRes;
import com.finfellows.domain.product.exception.InvalidFinancialProductException;
import com.finfellows.domain.product.exception.ProductTypeMismatchException;
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
    private final FinancialProductBookmarkRepository financialProductBookmarkRepository;

    @Override
    public PagedResponse<SearchFinancialProductRes> findDepositProducts(final UserPrincipal userPrincipal, final FinancialProductSearchCondition financialProductSearchCondition, final Pageable pageable) {
        Page<SearchFinancialProductRes> financialProducts = financialProductRepository.findFinancialProducts(financialProductSearchCondition, pageable, FinancialProductType.DEPOSIT, userPrincipal.getId());

        return new PagedResponse<>(financialProducts);
    }

    @Override
    public PagedResponse<SearchFinancialProductRes> findSavingProducts(final UserPrincipal userPrincipal, final FinancialProductSearchCondition financialProductSearchCondition, final Pageable pageable) {
        Page<SearchFinancialProductRes> financialProducts = financialProductRepository.findFinancialProducts(financialProductSearchCondition, pageable, FinancialProductType.SAVING, userPrincipal.getId());

        return new PagedResponse<>(financialProducts);
    }

    @Override
    public DepositDetailRes getDepositDetail(final UserPrincipal userPrincipal, final Long depositId) {
        FinancialProduct deposit = financialProductRepository.findById(depositId)
                .orElseThrow(InvalidFinancialProductException::new);

        if(!deposit.getFinancialProductType().equals(FinancialProductType.DEPOSIT))
            throw new ProductTypeMismatchException();

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

    @Override
    public SavingDetailRes getSavingDetail(final UserPrincipal userPrincipal, final Long savingId) {
        FinancialProduct saving = financialProductRepository.findById(savingId)
                .orElseThrow(InvalidFinancialProductException::new);

        if(!saving.getFinancialProductType().equals(FinancialProductType.SAVING))
            throw new ProductTypeMismatchException();

        List<FinancialProductOption> savingOptions = financialProductOptionRepository.findFinancialProductOptionsByFinancialProduct(saving);
        List<Integer> terms = savingOptions.stream()
                .map(FinancialProductOption::getSavingsTerm)
                .distinct()
                .sorted()
                .toList();

        FinancialProductOption maxOption = savingOptions.stream()
                .max(Comparator.comparing(FinancialProductOption::getMaximumPreferredInterestRate))
                .orElse(null);

        return SavingDetailRes.toDto(saving, maxOption, terms);
    }

    @Override
    public List<String> findBanks(final String bankGroupNo) {
        return financialProductRepository.findBanks(bankGroupNo);
    }

}