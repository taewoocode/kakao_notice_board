package com.example.kakao_notice_board.user.dto;

import lombok.Data;

@Data
public class UserRegistrationRequest {

    private String username;
    private String password;
    private String email;

}
