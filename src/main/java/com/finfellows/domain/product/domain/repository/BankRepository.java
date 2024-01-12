package com.finfellows.domain.product.domain.repository;

import com.finfellows.domain.product.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Bank findByBankName(String companyName);
}
