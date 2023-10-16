package com.wsss.basic.test.alloc;

public class TimeContext {
    private String key;
    private long startTime;

    public static TimeContext timer(String key) {
        TimeContext timeContext = new TimeContext();
        timeContext.startTime = System.currentTimeMillis();
        timeContext.key = key;
        return timeContext;
    }
}