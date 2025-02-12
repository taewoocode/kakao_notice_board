package com.example.kakao_notice_board.board.service;

import com.example.kakao_notice_board.board.domain.Post;
import com.example.kakao_notice_board.board.repository.PostRepository;
import com.example.kakao_notice_board.trace.TraceStatus;
import com.example.kakao_notice_board.trace.hellotrace.HelloTraceV1;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private final HelloTraceV1 trace;

    /**
     * 게시글 조회
     * @return
     */
    public List<Post> getAllPosts() {
        TraceStatus status = null;
        try {
            status = trace.begin("PostService.getAllPosts()");
            List<Post> posts = postRepository.findAll();
            trace.end(status);
            return posts;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    /**
     * 아이디로 조회
     * @param id
     * @return
     */
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    /**
     * 게시글 생성
     * @param post
     * @return
     */
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    /**
     * 게시글 삭제
     * @param id
     */
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
