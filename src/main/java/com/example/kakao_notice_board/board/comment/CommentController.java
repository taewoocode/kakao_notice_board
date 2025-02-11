package com.example.kakao_notice_board.board.comment;

import com.example.kakao_notice_board.board.domain.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    /**
     * 특정 게시글에 대한 댓글 조회
     * @param postId
     * @return
     */
    @GetMapping("/board/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /**
     * 댓글 생성
     * @param postId
     * @param comment
     * @return
     */
    @PostMapping("/board/{postId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        comment.setPost(new Post(postId)); // Post 객체를 생성하여 댓글에 설정
        Comment createdComment = commentService.createComment(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    /**
     * 댓글 삭제
     * @param postId
     * @param id
     * @return
     */
    @DeleteMapping("/board/{postId}/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
