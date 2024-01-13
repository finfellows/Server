package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.PolicyInfoBookmark;
import com.finfellows.domain.bookmark.domain.repository.PolicyInfoBookmarkRepository;
import com.finfellows.domain.bookmark.dto.PolicyInfoBookmarkRes;
import com.finfellows.domain.policyinfo.domain.PolicyInfo;
import com.finfellows.domain.policyinfo.domain.repository.PolicyInfoRepository;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.Message;
import com.finfellows.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PolicyInfoBookmarkServiceImpl implements BookmarkService {
    private final PolicyInfoBookmarkRepository policyInfoBookmarkRepository;
    private final UserRepository userRepository;
    private final PolicyInfoRepository policyInfoRepository;

    @Transactional
    @Override
    public Message insert(UserPrincipal userPrincipal, Long id) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);
        PolicyInfo policyInfo = policyInfoRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        PolicyInfoBookmark policyInfoBookmark = PolicyInfoBookmark.builder()
                .user(user)
                .policyInfo(policyInfo)
                .build();

        if (policyInfoBookmarkRepository.findByUserAndPolicyInfo(user, policyInfo).isPresent()) {
            return Message.builder()
                    .message("이미 즐겨찾기에 추가되었습니다.")
                    .build();
        }

        policyInfoBookmarkRepository.save(policyInfoBookmark);


        return Message.builder()
                .message("즐겨찾기 추가에 성공했습니다.")
                .build();
    }

    @Transactional
    @Override
    public Message delete(UserPrincipal userPrincipal, Long id) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);

        PolicyInfo policyInfo = policyInfoRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        PolicyInfoBookmark policyInfoBookmark = policyInfoBookmarkRepository.findByUserAndPolicyInfo(user, policyInfo).get();

        policyInfoBookmarkRepository.delete(policyInfoBookmark);


        return Message.builder()
                .message("즐겨찾기 삭제에 성공했습니다.")
                .build();
    }

    @Transactional
    public ResponseCustom<?> findBookmarks(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);

        List<PolicyInfoBookmark> bookmarks = policyInfoBookmarkRepository.findAllByUser(user);

        List<PolicyInfoBookmarkRes> policyInfoBookmarkResList = PolicyInfoBookmarkRes.toDto(bookmarks);

        return ResponseCustom.OK(policyInfoBookmarkResList);
    }
}

