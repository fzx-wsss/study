package com.wsss.basic.thread.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestThreadPoolExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        executor.execute(()->{
            System.out.println("1");
            int i = 1/0;
        });
        executor.execute(()->{
            System.out.println("2");
            int i = 1/0;
        });
        executor.execute(()->{
            System.out.println("3");
            int i = 1/0;
        });
    }
}
