package com.example.kakao_notice_board.board.comment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    List<Comment> getCommentsByPostId(Long postId);

    Comment createComment(Long postId, Comment comment);


    void deleteComment(Long id);


}
