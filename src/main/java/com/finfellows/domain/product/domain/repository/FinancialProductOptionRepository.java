package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.FinancialProductOption;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialProductOptionRepository extends JpaRepository<FinancialProductOption, Long> {

    List<FinancialProductOption> findFinancialProductOptionsByFinancialProduct(FinancialProduct financialProduct);

}
