package com.finfellows.domain.bookmark.dto;

import com.finfellows.domain.bookmark.domain.PostBookmark;
import com.finfellows.domain.educontent.domain.EduContent;
import com.finfellows.domain.newscontent.domain.NewsContent;
import com.finfellows.domain.post.domain.ContentType;
import com.finfellows.domain.post.domain.Post;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostBookmarkRes {
    private Boolean isLiked;
    private Long postId;
    private String title;
    private String content;
    private ContentType contentType;


    @Builder
    public PostBookmarkRes(Boolean isLiked, Long postId, String title, String content, ContentType contentType) {
        this.isLiked = isLiked;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.contentType = contentType;
    }



    public static List<PostBookmarkRes> toDto(List<PostBookmark> bookmarks) {
        List<PostBookmarkRes> results = new ArrayList<>();

        for (PostBookmark bookmark : bookmarks) {
            Post post = bookmark.getPost();
            ContentType contentType = post.getContentType();


            if (contentType == ContentType.EDU_CONTENT) {
                for (EduContent eduContent : post.getEduContent()) {
                    Boolean isLiked = Boolean.TRUE;
                    Long postId = eduContent.getId();
                    String title = eduContent.getTitle();
                    String content = eduContent.getContent();
                    results.add(new PostBookmarkRes(isLiked, postId, title, content, contentType));
                }
            }
            if (contentType == ContentType.NEWS_CONTENT) {
                for (NewsContent newsContent : post.getNewsContent()) {
                    Boolean isLiked = Boolean.TRUE;
                    Long postId = newsContent.getId();
                    String title = newsContent.getTitle();
                    String content = newsContent.getContent();
                    results.add(new PostBookmarkRes(isLiked, postId, title, content, contentType));
                }
            }
        }

        return results;
    }
}
