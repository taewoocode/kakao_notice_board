package com.example.kakao_notice_board.board.repository;

import com.example.kakao_notice_board.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA - 확장
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findAllWithTrace();
}
