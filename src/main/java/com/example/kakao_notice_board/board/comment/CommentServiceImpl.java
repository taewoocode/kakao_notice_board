package com.example.kakao_notice_board.board.comment;

import com.example.kakao_notice_board.board.comment.Comment;
import com.example.kakao_notice_board.board.comment.CommentService;
import com.example.kakao_notice_board.board.domain.Post;
import com.example.kakao_notice_board.board.notification.KakaoNotificationService;
import com.example.kakao_notice_board.board.repository.PostRepository;
import com.example.kakao_notice_board.user.domain.User;
import com.example.kakao_notice_board.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final KakaoNotificationService kakaoNotificationService;

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    @Transactional // 트랜잭션 관리
    public Comment createComment(Long postId, Comment comment) {
        findBypostToAuthor result = getFindBypostToAuthor(postId, comment);
        requestSettingPostUser(comment, result.post(), result.author());
        Comment savedComment = commentRepository.save(comment);
        sendMessageToAuthor(savedComment);
        return savedComment;
    }

    private findBypostToAuthor getFindBypostToAuthor(Long postId, Comment comment) {
        // Post 객체 조회 (postId로 찾기)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        // 댓글 작성자 조회 (comment.getAuthor()에서 userId로 User 객체 찾기)
        User author = userRepository.findById(comment.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new findBypostToAuthor(post, author);
    }

    private record findBypostToAuthor(Post post, User author) {}

    private void requestSettingPostUser(Comment comment, Post post, User author) {
        comment.setPost(post);
        comment.setAuthor(author);
    }

    private void sendMessageToAuthor(Comment savedComment) {
        String message = savedComment.getAuthor().getUsername() + "님이 댓글을 남기셨습니다.";
        String author = savedComment.getPost().getAuthor();
        kakaoNotificationService.sendNotification(author, message);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
