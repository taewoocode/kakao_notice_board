package com.example.kakao_notice_board.board.comment;

import com.example.kakao_notice_board.board.domain.Post;
import com.example.kakao_notice_board.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Comment {

    // 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // 글쓴이
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    // 내용
    @Column(nullable = false, length = 500)
    private String content;

    // 대댓글 기능 (부모 댓글)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 대댓글 리스트 (자식 댓글)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
}
