package com.example.kakao_notice_board.board.repository;

import com.example.kakao_notice_board.board.domain.Post;
import com.example.kakao_notice_board.trace.TraceStatus;
import com.example.kakao_notice_board.trace.hellotrace.HelloTraceV1;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final EntityManager entityManager;
    private final HelloTraceV1 trace;

    public PostRepositoryImpl(EntityManager entityManager, HelloTraceV1 trace) {
        this.entityManager = entityManager;
        this.trace = trace;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Post> findCustomPosts() {
        TraceStatus status = null;
        try {
            status = trace.begin("PostRepository.findCustomPosts");
            List<Post> posts = (List<Post>) entityManager.createQuery("SELECT p FROM Post p", Post.class);
            trace.end(status);
            return posts;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
