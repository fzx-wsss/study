package com.wsss.frame.netty.time.out;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.TimeUnit;

public class HashedWheelTimerTest {
    private static HashedWheelTimer timer = new HashedWheelTimer();

    public static void main(String[] args) throws Exception {
        Timeout timeout1 = timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("run");
                timeout.cancel();
            }
        }, 1, TimeUnit.HOURS);
        Timeout timeout2 = timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("run");
                timeout.cancel();
            }
        }, 10, TimeUnit.HOURS);
        System.out.println("start");
        timeout2.cancel();
        System.out.println("cancel");
        TimeUnit.SECONDS.sleep(10);
        TimeUnit.SECONDS.sleep(1000);
    }
}
