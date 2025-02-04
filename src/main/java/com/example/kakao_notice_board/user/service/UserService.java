package com.example.kakao_notice_board.user.service;

import com.example.kakao_notice_board.user.domain.User;
import com.example.kakao_notice_board.user.dto.UserRegistrationRequest;
import com.example.kakao_notice_board.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    public boolean authenticate(UserRegistrationRequest request) {
        User findUser = userRepository.findByUsername(request.getUsername());
        if (findUser != null && findUser.getEmail().equals(request.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
