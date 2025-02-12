package com.example.kakao_notice_board.trace;

import java.util.UUID;

public class TraceId {

    private String id;
    private int level;

    public TraceId(String id, int level) {
        this.id = createId();
        this.level = 0;
    }

    public TraceId() {
    }

    /**
     * createId
     * @return
     */
    private String createId() {
        return UUID.randomUUID().toString().substring(8);
    }

    /**
     * createNextTaceId
     * @return
     */
    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }


}
