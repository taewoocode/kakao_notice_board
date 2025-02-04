package com.example.kakao_notice_board.board.repository;

import com.example.kakao_notice_board.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
