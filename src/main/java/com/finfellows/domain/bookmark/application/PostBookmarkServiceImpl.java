package com.finfellows.domain.bookmark.application;

import com.finfellows.domain.bookmark.domain.EduContentBookmark;
import com.finfellows.domain.bookmark.domain.NewsContentBookmark;
import com.finfellows.domain.bookmark.domain.PostBookmark;
import com.finfellows.domain.bookmark.domain.repository.EduContentBookmarkRepository;
import com.finfellows.domain.bookmark.domain.repository.NewsContentBookmarkRepository;
import com.finfellows.domain.bookmark.domain.repository.PostBookmarkRepository;
import com.finfellows.domain.bookmark.dto.EduContentBookmarkRes;
import com.finfellows.domain.bookmark.dto.EduContentNewsContentBookmarkRes;
import com.finfellows.domain.bookmark.dto.NewsContentBookmarkRes;
import com.finfellows.domain.bookmark.dto.PostBookmarkRes;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.educontent.domain.repository.EduContentRepository;
import com.finfellows.domain.newscontent.domain.NewsContent;
import com.finfellows.domain.newscontent.domain.repository.NewsContentRepository;
import com.finfellows.domain.post.domain.ContentType;
import com.finfellows.domain.post.domain.Post;
import com.finfellows.domain.post.domain.repository.PostRepository;
import com.finfellows.domain.user.domain.User;
import com.finfellows.domain.user.domain.repository.UserRepository;
import com.finfellows.global.config.security.token.UserPrincipal;
import com.finfellows.global.payload.Message;
import com.finfellows.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostBookmarkServiceImpl {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostBookmarkRepository postBookmarkRepository;
    private final EduContentRepository eduContentRepository;
    private final NewsContentRepository newsContentRepository;
    private final EduContentBookmarkRepository eduContentBookmarkRepository;
    private final NewsContentBookmarkRepository newsContentBookmarkRepository;


    @Transactional
    public Message insert(UserPrincipal userPrincipal, Long id, ContentType contentType) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);


        if (contentType.equals(ContentType.EDU_CONTENT)) {
            EduContent eduContent = eduContentRepository.findById(id)
                    .orElseThrow(RuntimeException::new);

            EduContentBookmark eduContentBookmark = EduContentBookmark.builder()
                    .user(user)
                    .eduContent(eduContent)
                    .build();

            if (eduContentBookmarkRepository.findByUserAndEduContent(user, eduContent).isPresent()) {
                return Message.builder()
                        .message("이미 즐겨찾기에 추가되었습니다.")
                        .build();
            }

            eduContentBookmarkRepository.save(eduContentBookmark);

        } else if (contentType.equals(ContentType.NEWS_CONTENT)) {
            NewsContent newsContent = newsContentRepository.findById(id)
                    .orElseThrow(RuntimeException::new);

            NewsContentBookmark newsContentBookmark = NewsContentBookmark.builder()
                    .user(user)
                    .newsContent(newsContent)
                    .build();

            if (newsContentBookmarkRepository.findByUserAndNewsContent(user, newsContent).isPresent()) {
                return Message.builder()
                        .message("이미 즐겨찾기에 추가되었습니다.")
                        .build();
            }

            newsContentBookmarkRepository.save(newsContentBookmark);
        }

        return Message.builder()
                .message("즐겨찾기 추가에 성공했습니다.")
                .build();
    }

    @Transactional
    public Message delete(UserPrincipal userPrincipal, Long id, ContentType contentType) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);


        if (contentType.equals(ContentType.EDU_CONTENT)) {
            EduContent eduContent = eduContentRepository.findById(id)
                    .orElseThrow(RuntimeException::new);

            EduContentBookmark eduContentBookmark = eduContentBookmarkRepository.findByUserAndEduContent(user, eduContent).get();



            eduContentBookmarkRepository.delete(eduContentBookmark);

        } else if (contentType.equals(ContentType.NEWS_CONTENT)) {
            NewsContent newsContent = newsContentRepository.findById(id)
                    .orElseThrow(RuntimeException::new);

            NewsContentBookmark newsContentBookmark = newsContentBookmarkRepository.findByUserAndNewsContent(user, newsContent).get();


            newsContentBookmarkRepository.delete(newsContentBookmark);
        }


        return Message.builder()
                .message("즐겨찾기 삭제에 성공했습니다.")
                .build();
    }

    @Transactional
    public ResponseCustom<?> findBookmarks(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(RuntimeException::new);


        List<EduContentBookmark> eduContentBookmarks = eduContentBookmarkRepository.findAllByUser(user);

        List<NewsContentBookmark> newsContentBookmarks = newsContentBookmarkRepository.findAllByUser(user);

        List<EduContentBookmarkRes> eduContentBookmarkResList = EduContentBookmarkRes.toDto(eduContentBookmarks);

        List<NewsContentBookmarkRes> newsContentBookmarkResList = NewsContentBookmarkRes.toDto(newsContentBookmarks);


        EduContentNewsContentBookmarkRes eduContentNewsContentBookmarkRes = new EduContentNewsContentBookmarkRes(newsContentBookmarkResList, eduContentBookmarkResList);

        return ResponseCustom.OK(eduContentNewsContentBookmarkRes);
    }
}
