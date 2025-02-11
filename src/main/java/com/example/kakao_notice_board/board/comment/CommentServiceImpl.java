package com.example.kakao_notice_board.board.comment;

import com.example.kakao_notice_board.board.domain.Post;
import com.example.kakao_notice_board.board.notification.KakaoNotificationService;
import com.example.kakao_notice_board.board.repository.CommentRepository;
import com.example.kakao_notice_board.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // Kakao 알림 서비스 추가
    private final KakaoNotificationService kakaoNotificationService;


    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment createComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        //게시물 작성자에게 알림 전송
        Post post = savedComment.getPost();
        String author = post.getAuthor();

        //알림톡 전송
        String message = savedComment.getAuthor().getUsername() + "님이 댓글을 남기셨습니다.";
        kakaoNotificationService.sendNotification(author, message);
        return savedComment;
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
