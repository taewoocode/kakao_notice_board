package com.example.kakao_notice_board.board.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class KakaoNotificationService {

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    // 알림톡 발송 URL
    private static final String KAKAO_API_URL = "https://kapi.kakao.com/v2/alimtalk/send";

    // 알림톡 메시지 전송 메서드
    public void sendNotification(String receiverId, String message) {
        RestTemplate restTemplate = new RestTemplate();

        // 알림톡 메시지 JSON 형식
        String requestBody = String.format("{\"receiver_id\": \"%s\", \"message\": \"%s\"}", receiverId, message);

        /**
         * 요청 헤더 설정
         */
        HttpHeaders headers = new HttpHeaders();
        setContentHeader(headers, MediaType.APPLICATION_JSON,
                "Authorization",
                "Bearer " + kakaoApiKey);

        // 요청 본문 설정
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        requestPost(restTemplate, entity);
    }

    private static void requestPost(RestTemplate restTemplate, HttpEntity<String> entity) {
        // POST 요청 보내기
        try {
            ResponseEntity<String> response = restTemplate.exchange(KAKAO_API_URL, HttpMethod.POST, entity, String.class);
            // 응답 확인
            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("알림톡 전송 성공: " + response.getBody());
            } else {
                log.error("알림톡 전송 실패: " + response.getBody());
            }
        } catch (Exception e) {
            log.error("알림톡 전송 중 에러 발생: ", e);
        }
    }

    private void setContentHeader(HttpHeaders headers, MediaType contentType, String headerName, String headerValue) {
        headers.setContentType(contentType);
        headers.set(headerName, headerValue);
    }
}
