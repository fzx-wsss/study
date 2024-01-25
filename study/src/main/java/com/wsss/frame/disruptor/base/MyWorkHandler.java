package com.wsss.frame.disruptor.base;

import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.TimeUnit;

public class MyWorkHandler implements WorkHandler<MyEvent> {
    @Override
    public void onEvent(MyEvent event) throws Exception {
        System.out.println(Thread.currentThread().getName() + " Handling Work: " + event.getEventData());
        TimeUnit.SECONDS.sleep(1);
    }
}
