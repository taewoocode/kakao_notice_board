package com.example.kakao_notice_board.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String username;
}
