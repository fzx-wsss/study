package com.wsss.frame.disruptor.base;

import lombok.Data;

public class MyEvent {
    // 事件数据
    private String eventData;

    private long sequence;

    public void init(String eventData, long sequence) {
        this.sequence = sequence;
        this.eventData = eventData;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
// 其他字段和方法...

    // Getter 和 Setter 方法...
}
