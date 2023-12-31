package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.EduContentBookmark;
import com.finfellows.domain.bookmark.domain.repository.EduContentBookmarkRepository;
import com.finfellows.domain.bookmark.dto.EduContentBookmarkRes;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.educontent.domain.repository.EduContentRepository;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.Message;
import com.finfellows.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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

//        if (eduContentBookmarkRepository.findByUserAndEduContent(user, eduContent).isPresent()) {
//            return Message.builder()
//                    .message("이미 즐겨찾기 목록에 존재합니다.")
//                    .build();
//        }

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


    public ResponseCustom<List<EduContentBookmarkRes>> findBookmarks(UserPrincipal userPrincipal) {
        Optional<User> optionalUser = userRepository.findByEmail(userPrincipal.getEmail());

        User user = optionalUser.get();

        List<EduContentBookmark> bookmarks = eduContentBookmarkRepository.findAllByUser(user);


        List<EduContentBookmarkRes> eduContentBookmarkResList = EduContentBookmarkRes.toDto(bookmarks);


        return ResponseCustom.OK(eduContentBookmarkResList);
    }
}
