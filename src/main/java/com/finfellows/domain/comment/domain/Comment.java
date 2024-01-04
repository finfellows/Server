package com.finfellows.domain.comment.domain;

import com.finfellows.domain.common.BaseEntity;
import com.finfellows.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    //질문 내용 저장 칼럼 필요함.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id", updatable = false, nullable = false, unique = true)
    private Long commentId;

    @Column(name="comment_content")
    private String commentContent;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;

    @Builder
    public Comment(Long commentId, String commentContent, User userId){
        this.commentId=commentId;
        this.commentContent=commentContent;
        this.userId=userId;
    }
}
