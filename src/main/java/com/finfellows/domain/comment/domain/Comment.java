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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id", updatable = false, nullable = false, unique = true)
    private Long commentId;

    @Column(name = "greeting")
    private String greeting;

    @Column(name = "question")
    private String question;

    @Column(name="answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Comment(String question, String greeting, String answer, User user){
        this.greeting=greeting;
        this.question=question;
        this.answer=answer;
        this.user=user;
    }
}
