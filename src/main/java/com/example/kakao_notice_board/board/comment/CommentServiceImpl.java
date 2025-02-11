package com.example.kakao_notice_board.board.comment;

import com.example.kakao_notice_board.board.comment.Comment;
import com.example.kakao_notice_board.board.comment.CommentService;
import com.example.kakao_notice_board.board.domain.Post;
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
//    private final KakaoNotificationService kakaoNotificationService;

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    /**
     *
     * @param postId
     * @param comment
     * @return
     */
    @Override
    @Transactional // 트랜잭션 관리
    public Comment createComment(Long postId, Comment comment) {
        // 디버깅 로그 추가
        log.debug("Creating comment for postId: {}", postId);

        findBypostToAuthor result = getFindBypostToAuthor(postId, comment);

        // 디버깅 로그 추가
        log.debug("Found post: {} and author: {}", result.post().getId(), result.author().getId());

        requestSettingPostUser(comment, result.post(), result.author());
        Comment savedComment = commentRepository.save(comment);

        // 디버깅 로그 추가
        log.debug("Saved comment: {}", savedComment.getId());

        sendMessageToAuthor(savedComment);
        return savedComment;
    }

    private findBypostToAuthor getFindBypostToAuthor(Long postId, Comment comment) {
        // 디버깅 로그 추가
        log.debug("Fetching post with id: {}", postId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        // 디버깅 로그 추가
        log.debug("Fetching author with id: {}", comment.getAuthor().getId());

        User author = userRepository.findById(comment.getAuthor().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new findBypostToAuthor(post, author);
    }

    private record findBypostToAuthor(Post post, User author) {}

    private void requestSettingPostUser(Comment comment, Post post, User author) {
        comment.setPost(post);
        comment.setAuthor(author);
    }

    /**
     *
     * @param savedComment
     */
    private void sendMessageToAuthor(Comment savedComment) {
        String message = savedComment.getAuthor().getUsername() + "님이 댓글을 남기셨습니다.";
        User author = savedComment.getPost().getAuthor();
//        kakaoNotificationService.sendNotification(author, message);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
