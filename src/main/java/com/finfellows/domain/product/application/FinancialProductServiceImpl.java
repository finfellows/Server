package com.finfellows.domain.product.application;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.bookmark.domain.repository.CmaBookmarkRepository;
import com.finfellows.domain.bookmark.domain.repository.FinancialProductBookmarkRepository;
import com.finfellows.domain.product.domain.*;
import com.finfellows.domain.product.domain.repository.BankRepository;
import com.finfellows.domain.product.domain.repository.CmaRepository;
import com.finfellows.domain.product.domain.repository.FinancialProductOptionRepository;
import com.finfellows.domain.product.domain.repository.FinancialProductRepository;
import com.finfellows.domain.product.dto.condition.CmaSearchCondition;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.request.BankUploadReq;
import com.finfellows.domain.product.dto.response.*;
import com.finfellows.domain.product.exception.InvalidFinancialProductException;
import com.finfellows.domain.product.exception.ProductTypeMismatchException;
import com.finfellows.global.config.security.token.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FinancialProductServiceImpl implements FinancialProductService {

    private final FinancialProductRepository financialProductRepository;
    private final FinancialProductOptionRepository financialProductOptionRepository;
    private final FinancialProductBookmarkRepository financialProductBookmarkRepository;
    private final CmaBookmarkRepository cmaBookmarkRepository;
    private final CmaRepository cmaRepository;
    private final BankRepository bankRepository;

    @Override
    public Page<SearchFinancialProductRes> findDepositProducts(final UserPrincipal userPrincipal, final FinancialProductSearchCondition financialProductSearchCondition, final Pageable pageable) {
        if (userPrincipal != null) {
            return financialProductRepository.findFinancialProductsWithAuthorization(financialProductSearchCondition, pageable, FinancialProductType.DEPOSIT, userPrincipal.getId());
        }
        return financialProductRepository.findFinancialProducts(financialProductSearchCondition, pageable, FinancialProductType.DEPOSIT);
    }

    @Override
    public Page<SearchFinancialProductRes> findSavingProducts(final UserPrincipal userPrincipal, final FinancialProductSearchCondition financialProductSearchCondition, final Pageable pageable) {
        if (userPrincipal != null) {
            return financialProductRepository.findFinancialProductsWithAuthorization(financialProductSearchCondition, pageable, FinancialProductType.SAVING, userPrincipal.getId());
        }
        return financialProductRepository.findFinancialProducts(financialProductSearchCondition, pageable, FinancialProductType.SAVING);
    }

    @Override
    public DepositDetailRes getDepositDetail(final UserPrincipal userPrincipal, final Long depositId) {
        FinancialProduct deposit = financialProductRepository.findById(depositId)
                .orElseThrow(InvalidFinancialProductException::new);

        Bank bank = bankRepository.findByBankName(deposit.getBankName());

        Optional<FinancialProductBookmark> bookmark = Optional.empty();
        if (userPrincipal != null)
            bookmark = financialProductBookmarkRepository.findByUserAndFinancialProduct(userPrincipal.getUser(), deposit);

        if (!deposit.getFinancialProductType().equals(FinancialProductType.DEPOSIT))
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

        return DepositDetailRes.toDto(bookmark, bank.getBankLogoUrl(), bank.getBankUrl(), deposit, maxOption, terms);
    }

    @Override
    public SavingDetailRes getSavingDetail(final UserPrincipal userPrincipal, final Long savingId) {
        FinancialProduct saving = financialProductRepository.findById(savingId)
                .orElseThrow(InvalidFinancialProductException::new);

        Bank bank = bankRepository.findByBankName(saving.getBankName());

        Optional<FinancialProductBookmark> bookmark = Optional.empty();
        if (userPrincipal != null)
            bookmark = financialProductBookmarkRepository.findByUserAndFinancialProduct(userPrincipal.getUser(), saving);

        if (!saving.getFinancialProductType().equals(FinancialProductType.SAVING))
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

        return SavingDetailRes.toDto(bookmark, bank.getBankLogoUrl(), bank.getBankUrl(), saving, maxOption, terms);
    }

    @Override
    public List<SearchBankRes> findBanks(final String[] bankGroupNo) {
        return financialProductRepository.findBanks(bankGroupNo);
    }

    @Override
    public Page<SearchCmaRes> findCmaProducts(UserPrincipal userPrincipal, CmaSearchCondition cmaSearchCondition, Pageable pageable) {
        if (userPrincipal != null) {
            return financialProductRepository.findCmaProductsWithAuthorization(cmaSearchCondition, pageable, userPrincipal.getId());
        }
        return financialProductRepository.findCmaProducts(cmaSearchCondition, pageable);
    }

    @Override
    public CmaDetailRes getCmaDetail(UserPrincipal userPrincipal, Long cmaId) {
        CMA cma = cmaRepository.findById(cmaId)
                .orElseThrow(InvalidFinancialProductException::new);

        Bank bank = bankRepository.findByBankName(cma.getBankName());

        Optional<CmaBookmark> bookmark = Optional.empty();
        if (userPrincipal != null)
            bookmark = cmaBookmarkRepository.findCmaBookmarkByCmaAndUser(cma, userPrincipal.getUser());

        return CmaDetailRes.toDto(cma, bookmark, bank.getBankLogoUrl(), bank.getBankUrl());
    }

    @Override
    @Transactional
    public Void bankUpload(BankUploadReq bankUploadReq, String bankLogoImg) {
        Bank bank = Bank.builder()
                .bankName(bankUploadReq.getKor_co_nm())
                .bankCode(bankUploadReq.getFin_co_no())
                .bankLogoUrl(bankLogoImg)
                .bankUrl(bankUploadReq.getHomp_url())
                .bankTel(bankUploadReq.getCal_tel())
                .build();

        bankRepository.save(bank);

        return null;
    }

}