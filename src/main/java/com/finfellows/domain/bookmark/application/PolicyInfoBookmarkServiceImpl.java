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
        Optional<User> optionalUser = userRepository.findByEmail(userPrincipal.getEmail());
        Optional<PolicyInfo> optionalPolicyInfo = policyInfoRepository.findById(id);

        User user = optionalUser.get();
        PolicyInfo policyInfo = optionalPolicyInfo.get();

        PolicyInfoBookmark policyInfoBookmark = PolicyInfoBookmark.builder()
                .user(user)
                .policyInfo(policyInfo)
                .build();

        policyInfoBookmarkRepository.save(policyInfoBookmark);


        return Message.builder()
                .message("즐겨찾기 추가에 성공했습니다.")
                .build();
    }

    @Transactional
    @Override
    public Message delete(UserPrincipal userPrincipal, Long id) {
        Optional<User> optionalUser = userRepository.findByEmail(userPrincipal.getEmail());
        Optional<PolicyInfo> optionalPolicyInfo = policyInfoRepository.findById(id);

        User user = optionalUser.get();
        PolicyInfo policyInfo = optionalPolicyInfo.get();

        PolicyInfoBookmark policyInfoBookmark = policyInfoBookmarkRepository.findByUserAndPolicyInfo(user, policyInfo).get();

        policyInfoBookmarkRepository.delete(policyInfoBookmark);


        return Message.builder()
                .message("즐겨찾기 삭제에 성공했습니다.")
                .build();
    }

    public ResponseCustom<?> findBookmarks(UserPrincipal userPrincipal) {
        Optional<User> optionalUser = userRepository.findByEmail(userPrincipal.getEmail());

        User user = optionalUser.get();

        List<PolicyInfoBookmark> bookmarks = policyInfoBookmarkRepository.findAllByUser(user);

        List<PolicyInfoBookmarkRes> policyInfoBookmarkResList = PolicyInfoBookmarkRes.toDto(bookmarks);

        return ResponseCustom.OK(policyInfoBookmarkResList);
    }
}
