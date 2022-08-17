package com.wsss.basic.thread.suport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest2 {
 
     public static void main(String[] args) throws InterruptedException {
 
         ThreadA ta = new ThreadA("ta");

        System.out.println(Thread.currentThread().getName()+" start ta");
        ta.start();

        System.out.println(Thread.currentThread().getName()+" block");
        Thread.sleep(5);
        // 主线程阻塞
        LockSupport.park(ta);
        Thread.sleep(5);
         LockSupport.unpark(ta);
        System.out.println(Thread.currentThread().getName()+" continue");
    }

    static class ThreadA extends Thread{

        public ThreadA(String name) {
            super(name);
        }

        public void run() {
            for(int i=0;i<1000;i++) {
                System.out.println(i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}