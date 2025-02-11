package com.example.kakao_notice_board.board.controller;

import com.example.kakao_notice_board.board.domain.Post;
import com.example.kakao_notice_board.board.service.PostService;
import com.example.kakao_notice_board.board.service.PostServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("board/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시글 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * 특정 게시글 조회
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> findId = postService.getPostById(id);
        return findId.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 게시글 생성
     *
     * @param post
     * @return
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPost);  // 변경: CREATED 상태 코드 반환
    }

    /**
     * 게시글 삭제
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
