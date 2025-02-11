package com.example.kakao_notice_board.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Post {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdAt;

    // Long id를 받는 생성자 추가
    public Post(Long id) {
        this.id = id;
    }

    // 기본 생성자도 필요
    public Post() {
    }
}
