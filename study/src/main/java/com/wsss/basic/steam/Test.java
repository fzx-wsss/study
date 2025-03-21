package com.wsss.basic.steam;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            set.add(String.valueOf(i));
        }
        Set<Thread> threads = new HashSet<>();
        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                for(int j = 0; j < 10000; j++) {
                    set.stream().filter(s -> s.length() > 2).map(s->new BigDecimal(s)).reduce(BigDecimal::add);
                }
            });
            threads.add(thread);
        }

        long start = System.currentTimeMillis();

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
