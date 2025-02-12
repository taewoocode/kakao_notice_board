package com.example.kakao_notice_board.trace.hellotrace;

import com.example.kakao_notice_board.trace.TraceId;
import com.example.kakao_notice_board.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 실행시간, 트랜잭션 ID 관리
 */
@Slf4j
@Component
public class HelloTraceV1 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    /**
     * message 입력 받아서 log 출력
     */
    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    /**
     * 트랜잭션이 정상적으로 종료되었음을 로그로 출력
     * @param status 트랜잭션 상태 정보
     */
    public void end(TraceStatus status) {
        complete(status, null);
    }

    /**
     * 트랜잭션 도중 발생한 예외를 로그로 출력
     * @param status 트랜잭션 상태 정보
     * @param e 발생한 예외 객체
     */
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    /**
     * 트랜잭션 완료 또는 예외 상황을 로그로 출력
     * log Mapping은 {}, argument 1 : 1
     * @param status 트랜잭션 상태 정보
     * @param e 발생한 예외 객체 (null이면 정상 종료로 간주)
     */
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
                    resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());
        }
    }

    /**
     * 계층 구조에 따라 로그 메시지 앞에 공백과 접두어를 추가
     * @param prefix 접두어 (START, COMPLETE, EXCEPTION 중 하나)
     * @param level 계층 수준 (트랜잭션 중첩 레벨)
     * @return 계층 구조에 맞춘 접두어 문자열
     * level=0
     * level=1 |-->
     * level=2     |-->
     */
    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
}
