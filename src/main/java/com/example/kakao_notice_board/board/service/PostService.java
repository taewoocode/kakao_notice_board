package com.example.kakao_notice_board.board.service;

import com.example.kakao_notice_board.board.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {


    List<Post> getAllPosts();

    /**
     * 아이디로 조회
     * @param id
     * @return
     */
    Optional<Post> getPostById(Long id);

    /**
     * 게시글 생성
     * @param post
     * @return
     */
    Post createPost(Post post);

    /**
     * 게시글 삭제
     * @param id
     */
    void deletePost(Long id);
}
