package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.EduContentBookmark;
import com.finfellows.domain.bookmark.domain.repository.EduContentBookmarkRepository;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.educontent.domain.repository.EduContentRepository;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EduContentBookmarkServiceImpl implements BookmarkService{
    private final EduContentBookmarkRepository eduContentBookmarkRepository;
    private final UserRepository userRepository;
    private final EduContentRepository eduContentRepository;
    @Transactional
    @Override
    public Message insert(UserPrincipal userPrincipal, Long id) {
        Optional<User> optionalUser = userRepository.findByEmail(userPrincipal.getEmail());
        Optional<EduContent> optionalEduContent = eduContentRepository.findById(id);

        User user = optionalUser.get();
        EduContent eduContent = optionalEduContent.get();


        EduContentBookmark eduContentBookmark = EduContentBookmark.builder()
                .user(user)
                .eduContent(eduContent)
                .build();

        eduContentBookmarkRepository.save(eduContentBookmark);

        return Message.builder()
                .message("즐겨찾기 추가에 성공했습니다.")
                .build();

    }

    @Transactional
    @Override
    public Message delete(UserPrincipal userPrincipal, Long id) {

        Optional<User> optionalUser = userRepository.findByEmail(userPrincipal.getEmail());
        Optional<EduContent> optionalEduContent = eduContentRepository.findById(id);

        User user = optionalUser.get();
        EduContent eduContent = optionalEduContent.get();

        EduContentBookmark eduContentBookmark = eduContentBookmarkRepository.findByUserAndEduContent(user, eduContent).get();

        eduContentBookmarkRepository.delete(eduContentBookmark);


        return Message.builder()
                .message("즐겨찾기 삭제에 성공했습니다.")
                .build();
    }


}
