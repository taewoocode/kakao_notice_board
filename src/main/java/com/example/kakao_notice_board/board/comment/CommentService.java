package com.example.kakao_notice_board.board.comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByPostId(Long postId);

    Comment createComment(Comment comment);

    void deleteComment(Long id);


}
