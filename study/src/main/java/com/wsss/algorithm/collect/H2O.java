package com.wsss.algorithm.collect;


import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class H2O {
    private AtomicInteger oCount = new AtomicInteger(0);
    private AtomicInteger hCount = new AtomicInteger(0);
    private AtomicInteger sum = new AtomicInteger(0);
    public H2O() {
        
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
		int count = hCount.getAndIncrement() / 2;
        while(true) {
            if(count * 3 > sum.get()) {
                TimeUnit.NANOSECONDS.sleep(1);
                continue;
            }
            break;
        }
        releaseHydrogen.run();
        sum.getAndIncrement();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        Integer count = oCount.getAndIncrement();
        while(true) {
            if(count * 3 > sum.get()) {
                TimeUnit.NANOSECONDS.sleep(1);
                continue;
            }
            break;
        }
        releaseOxygen.run();
        sum.getAndIncrement();
    }

}