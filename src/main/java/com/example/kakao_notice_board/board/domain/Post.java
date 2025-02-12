package com.example.kakao_notice_board.board.domain;

import com.example.kakao_notice_board.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    /** Post User 연결 **/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;


    private String createdAt;

    // Long id를 받는 생성자 추가
    public Post(Long id) {
        this.id = id;
    }

    public Post() {
    }
}
