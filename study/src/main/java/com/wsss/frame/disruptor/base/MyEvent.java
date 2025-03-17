package com.wsss.frame.disruptor.base;

import lombok.Data;

@Data
public class MyEvent {
    // 事件数据
    private MyData eventData;

    private long sequence;

    public void init(MyData eventData, long sequence) {
        this.sequence = sequence;
        this.eventData = eventData;
    }

}
