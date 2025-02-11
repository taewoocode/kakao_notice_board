package com.example.kakao_notice_board.board.notification;

import com.example.kakao_notice_board.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KakaoNotificationService {

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.access.token}") // 액세스 토큰을 properties에서 관리
    private String accessToken;

    // 알림톡 API 엔드포인트
    private static final String KAKAO_API_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

    // 알림톡 메시지 전송 메서드
    public void sendNotification(User receiver, String message) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 본문 구성
        Map<String, Object> templateObject = new HashMap<>();
        templateObject.put("object_type", "text");
        templateObject.put("text", message);
        templateObject.put("link", new HashMap<>()); // 링크는 필요에 따라 추가

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("template_object", templateObject);

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        // 요청 엔티티 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

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
}