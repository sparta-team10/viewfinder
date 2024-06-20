package com.sparta.viewfinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    // post가 삭제되면 함께 삭제되도록 하기
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphan)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // user가 삭제되면 함께 삭제
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;*/

    private Long postId;
    private Long userId;

    @Setter
    @Column(nullable = false)
    private String content;

    public Comment(User user, Post post, String content) {
        //this.user = user;
        //this.post = post;
        this.content = content;
    }

    // 임시 생성자
    public Comment(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }
}