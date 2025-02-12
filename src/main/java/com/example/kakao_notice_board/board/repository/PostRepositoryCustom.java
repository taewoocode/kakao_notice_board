package com.example.kakao_notice_board.board.repository;

import com.example.kakao_notice_board.board.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findCustomPosts();
}
