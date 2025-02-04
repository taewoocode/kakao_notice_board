package com.example.kakao_notice_board.board.controller;

import com.example.kakao_notice_board.board.domain.Post;
import com.example.kakao_notice_board.board.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api/posts")
public class PostController {

    private PostService postService;

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
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(
            @PathVariable Long id) {
        Optional<Post> findId = postService.getPostById(id);
        return findId.map(ResponseEntity::ok).orElseGet(()
                -> ResponseEntity.notFound().build());
    }

    /**
     * 게시글 생성
     *
     * @param post
     * @return
     */
    @PostMapping
    public ResponseEntity<Post> createPost(
            @RequestBody Post post) {
        Post createPost = postService.createPost(post);
        return ResponseEntity.ok(createPost);
    }

    /**
     * 게시글 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
