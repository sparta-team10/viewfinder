package com.sparta.viewfinder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /*
    // user가 삭제되면 함께 삭제
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;*/

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
    public Comment(Long userId, Post post, String content) {
        this.userId = userId;
        this.post = post;
        this.content = content;
    }
}