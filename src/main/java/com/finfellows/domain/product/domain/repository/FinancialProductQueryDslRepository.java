package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.dto.condition.DepositSearchCondition;
import com.finfellows.domain.product.dto.response.SearchDepositRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FinancialProductQueryDslRepository {

    Page<SearchDepositRes> findFinancialProducts(DepositSearchCondition depositSearchCondition, Pageable pageable);

}
