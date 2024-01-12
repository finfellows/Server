package com.finfellows.domain.product.application;

import com.finfellows.domain.product.dto.condition.CmaSearchCondition;
import com.finfellows.domain.product.dto.condition.FinancialProductSearchCondition;
import com.finfellows.domain.product.dto.request.BankUploadReq;
import com.finfellows.domain.product.dto.response.*;
import com.finfellows.global.config.security.token.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FinancialProductService {

    Page<SearchFinancialProductRes> findDepositProducts(UserPrincipal userPrincipal, FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable);
    Page<SearchFinancialProductRes> findSavingProducts(UserPrincipal userPrincipal, FinancialProductSearchCondition financialProductSearchCondition, Pageable pageable);
    DepositDetailRes getDepositDetail(UserPrincipal userPrincipal, Long depositId);
    SavingDetailRes getSavingDetail(UserPrincipal userPrincipal, Long savingId);
    List<SearchBankRes> findBanks(String[] bankType);
    Page<SearchCmaRes> findCmaProducts(UserPrincipal userPrincipal, CmaSearchCondition cmaSearchCondition, Pageable pageable);
    CmaDetailRes getCmaDetail(UserPrincipal userPrincipal, Long cmaId);
    Void bankUpload(BankUploadReq bankUploadReq, String bankLogoImg) throws IOException;

}
