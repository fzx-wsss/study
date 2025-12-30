package com.wsss.basic.test.alloc.example;

public class TestAllocation {
    public static void main(String[] args) {
        Monitor.timerInstanceManager = new TimerInstanceManager();
        while (true) {
            Monitor.TimeContext ctx = Monitor.timer("123");
            ctx.end();
        }
    }
}
