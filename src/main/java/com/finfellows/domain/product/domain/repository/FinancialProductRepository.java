package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.product.domain.FinancialProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialProductRepository extends JpaRepository<FinancialProduct, Long>, FinancialProductQueryDslRepository {

}
