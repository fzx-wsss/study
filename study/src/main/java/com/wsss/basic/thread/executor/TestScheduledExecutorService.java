package com.wsss.basic.thread.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduledExecutorService {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder().setDaemon(false).setNameFormat("matching-monitor").build());

        executor.scheduleWithFixedDelay(()->{
            System.out.println("1");
            int i = 1/0;
        },2,2, TimeUnit.SECONDS);

        executor.scheduleWithFixedDelay(()->{
            System.out.println("2");
//            int i = 1/0;
        },2,2, TimeUnit.SECONDS);


    }
}
