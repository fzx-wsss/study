package com.wsss.basic.test.alloc.example;

class Monitor {

    static TimerInstanceManager timerInstanceManager;

    static TimeContext timer(String key) {
        TimeContext ctx = new TimeContext();
        ctx.key = key;
        ctx.startTime = System.nanoTime();
        return ctx;
    }

    static class TimeContext {
        String key;
        long startTime;

        void end() {
            long cost = System.nanoTime() - startTime;
            timerInstanceManager.record(key, "",cost);
        }
    }
}