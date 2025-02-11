package com.example.kakao_notice_board.board.notification;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class KakaoNotificationService {

    private static final String KAKAO_API_URL = "https://kapi.kakao.com/v2/alimtalk/send";

    public void sendCommentNotification(String userName, String commentContent) {
        // 카카오톡 알림톡 API 호출을 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer YOUR_ACCESS_TOKEN"); // 카카오 API 토큰 설정

        // 카카오 알림톡 API 요청 데이터 생성 (여기서는 예시로 JSON 형식으로 설정)
        String requestBody = String.format("{\"receiver_id\": \"%s\", \"message\": \"[%s]님이 게시글에 댓글을 남겼습니다: %s\"}",
                userName, userName, commentContent);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // 카카오 API 호출 (RestTemplate 사용)
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_API_URL, HttpMethod.POST, entity, String.class);

        // 결과 로그
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("카카오톡 알림 전송 성공");
        } else {
            System.out.println("카카오톡 알림 전송 실패");
        }
    }
}
