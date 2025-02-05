package com.example.kakao_notice_board.user.service;

import com.example.kakao_notice_board.user.domain.User;
import com.example.kakao_notice_board.user.dto.UserRegistrationRequest;
import com.example.kakao_notice_board.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public User registerUser(UserRegistrationRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEmail(request.getEmail());

            User savedUser = userRepository.save(user);
            logger.info("User registered: {}", savedUser);

            return savedUser;
        } catch (Exception e) {
            logger.error("회원가입 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("회원가입 실패", e);
        }
    }


    /**
     *
     * @param request
     * @return
     */
    @Transactional(readOnly = true)
    public boolean authenticate(UserRegistrationRequest request) {
        User findUser = userRepository.findByUsername(request.getUsername());
        if (findUser == null) {
            // 로그 추가
            logger.info("사용자 " + request.getUsername() + "를 찾을 수 없습니다.");
            return false; // 사용자 존재하지 않음
        }

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), findUser.getPassword());
        if (passwordMatches) {
            return true; // 비밀번호 일치
        } else {
            return false; // 비밀번호 불일치
        }
    }


}
