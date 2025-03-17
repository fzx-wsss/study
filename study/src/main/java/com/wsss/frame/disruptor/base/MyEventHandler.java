package com.wsss.frame.disruptor.base;

import com.lmax.disruptor.EventHandler;

import java.util.concurrent.TimeUnit;

public class MyEventHandler implements EventHandler<MyEvent> {
    @Override
    public void onEvent(MyEvent event, long sequence, boolean endOfBatch) throws Exception {
        // 处理事件的逻辑
        event.setEventData(null);
//        System.out.println(Thread.currentThread().getName() + " Handling event: " + event.getEventData() +" endOfBatch:"+ endOfBatch);
    }
}
