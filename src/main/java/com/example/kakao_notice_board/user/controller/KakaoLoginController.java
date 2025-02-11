package com.kakaologin.demo.controller;

import com.example.kakao_notice_board.user.dto.KakaoUserInfoResponseDto;
import com.example.kakao_notice_board.user.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam("code") String code) {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        // User 로그인, 또는 회원가입 로직 추가
        return new ResponseEntity<>(HttpStatus.OK);
    }
}