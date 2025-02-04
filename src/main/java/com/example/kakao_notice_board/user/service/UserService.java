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

    public User registerUser(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        User savedUser = userRepository.save(user);
        logger.info("User registered: {}", savedUser);
        return savedUser;
    }

    /**
     *
     * @param request
     * @return
     */
    public boolean authenticate(UserRegistrationRequest request) {
        User findUser = userRepository.findByUsername(request.getUsername());
        if (findUser != null && passwordEncoder.matches(request.getPassword(), findUser.getPassword())) {
            logger.info("Authentication successful for user: {}", findUser.getUsername());
            return true;
        } else {
            logger.info("Authentication failed for user: {}", request.getUsername());
            return false;
        }
    }
}
