package com.example.kakao_notice_board.user.controller;

import com.example.kakao_notice_board.user.dto.UserRegistrationRequest;
import com.example.kakao_notice_board.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRegistrationRequest request) {
        boolean isAuthenticated = userService.authenticate(request);
        if (isAuthenticated) {
            return ResponseEntity.ok().body(Map.of("message", "로그인 성공"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "로그인 실패: 사용자 이름 또는 비밀번호가 잘못되었습니다."));
        }
    }
}
