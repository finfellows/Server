package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.product.domain.FinancialProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialProductRepository extends JpaRepository<FinancialProduct, Long> {
}
