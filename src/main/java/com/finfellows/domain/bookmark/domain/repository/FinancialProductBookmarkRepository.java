package com.finfellows.domain.bookmark.domain.repository;

import com.finfellows.domain.bookmark.domain.EduContentBookmark;
import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialProductBookmarkRepository extends JpaRepository<FinancialProductBookmark, Long> {
    Optional<FinancialProductBookmark> findByUserAndFinancialProduct(User user, FinancialProduct financialProduct);


    List<FinancialProductBookmark> findAllByUser(User user);
}
