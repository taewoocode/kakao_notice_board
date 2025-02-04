package com.example.kakao_notice_board.user.controller;

import com.example.kakao_notice_board.user.dto.UserRegistrationRequest;
import com.example.kakao_notice_board.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody UserRegistrationRequest request
    ) {
        userService.registerUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> loginUser(
            @RequestBody UserRegistrationRequest request
    ) {
        boolean isAuthenticated = userService.authenticate(request);
        if (isAuthenticated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
