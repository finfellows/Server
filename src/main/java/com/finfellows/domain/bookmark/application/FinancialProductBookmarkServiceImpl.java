package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.CmaBookmark;
import com.finfellows.domain.bookmark.domain.repository.CmaBookmarkRepository;
import com.finfellows.domain.bookmark.domain.FinancialProductBookmark;
import com.finfellows.domain.bookmark.domain.repository.FinancialProductBookmarkRepository;
import com.finfellows.domain.bookmark.dto.CmaBookmarkRes;
import com.finfellows.domain.bookmark.dto.CmaFinancialProductBookmarkRes;
import com.finfellows.domain.bookmark.dto.FinancialProductBookmarkRes;
import com.finfellows.domain.product.domain.CMA;
import com.finfellows.domain.product.domain.FinancialProduct;
import com.finfellows.domain.product.domain.repository.CmaRepository;
import com.finfellows.domain.product.domain.repository.FinancialProductRepository;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.Message;
import com.finfellows.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinancialProductBookmarkServiceImpl implements BookmarkService{
    private final FinancialProductBookmarkRepository financialProductBookmarkRepository;
    private final UserRepository userRepository;
    private final FinancialProductRepository financialProductRepository;
    private final CmaRepository cmaRepository;
    private final CmaBookmarkRepository cmaBookmarkRepository;


    @Transactional
    @Override
    public Message insert(UserPrincipal userPrincipal, Long id) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);

        FinancialProduct financialProduct = financialProductRepository.findById(id)
                .orElseThrow(RuntimeException::new);


        FinancialProductBookmark financialProductBookmark = FinancialProductBookmark.builder()
                .user(user)
                .financialProduct(financialProduct)
                .build();

        financialProductBookmarkRepository.save(financialProductBookmark);

        return Message.builder()
                .message("즐겨찾기 추가에 성공했습니다.")
                .build();
    }

    @Transactional
    @Override
    public Message delete(UserPrincipal userPrincipal, Long id) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);

        FinancialProduct financialProduct = financialProductRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        FinancialProductBookmark financialProductBookmark = financialProductBookmarkRepository.findByUserAndFinancialProduct(user, financialProduct).get();

        financialProductBookmarkRepository.delete(financialProductBookmark);


        return Message.builder()
                .message("즐겨찾기 삭제에 성공했습니다.")
                .build();
    }

    @Transactional
    public ResponseCustom<CmaFinancialProductBookmarkRes> findBookmarks(UserPrincipal userPrincipal) {
        User user = userRepository.findByEmail(userPrincipal.getEmail())
                .orElseThrow(RuntimeException::new);


        List<FinancialProductBookmark> bookmarks = financialProductBookmarkRepository.findAllByUser(user);

        List<CmaBookmark> cmaBookmarks = cmaBookmarkRepository.findAllByUser(user);


        List<FinancialProductBookmarkRes> financialProductBookmarkResList = FinancialProductBookmarkRes.toDto(bookmarks);
        List<CmaBookmarkRes> cmaBookmarkResList = CmaBookmarkRes.toDto(cmaBookmarks);

        CmaFinancialProductBookmarkRes cmaFinancialProductBookmarkRes = new CmaFinancialProductBookmarkRes(financialProductBookmarkResList, cmaBookmarkResList);



        return ResponseCustom.OK(cmaFinancialProductBookmarkRes);
    }

    @Transactional
    public Message cmaInsert(UserPrincipal userPrincipal, Long cmaId) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);

        CMA cma = cmaRepository.findById(cmaId)
                .orElseThrow(RuntimeException::new);

        CmaBookmark cmaBookmark = CmaBookmark.builder()
                .user(user)
                .cma(cma)
                .build();

        cmaBookmarkRepository.save(cmaBookmark);

        return Message.builder()
                .message("즐겨찾기 추가에 성공했습니다.")
                .build();
    }

    @Transactional
    public Message cmaDelete(UserPrincipal userPrincipal, Long cmaId) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);

        CMA cma = cmaRepository.findById(cmaId)
                .orElseThrow(RuntimeException::new);

        CmaBookmark cmaBookmark = CmaBookmark.builder()
                .user(user)
                .cma(cma)
                .build();

        cmaBookmarkRepository.delete(cmaBookmark);

        return Message.builder()
                .message("즐겨찾기 삭제에 성공했습니다.")
                .build();
    }

}
