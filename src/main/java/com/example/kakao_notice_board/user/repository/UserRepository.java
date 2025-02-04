package com.example.kakao_notice_board.user.repository;

import com.example.kakao_notice_board.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
