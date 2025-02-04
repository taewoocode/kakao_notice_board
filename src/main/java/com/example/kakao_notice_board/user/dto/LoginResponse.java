package com.example.kakao_notice_board.user.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String username;
}
