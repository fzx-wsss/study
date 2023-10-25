package com.wsss.frame.jctools;

import org.jctools.maps.NonBlockingHashMap;
import org.jctools.maps.NonBlockingIdentityHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class NonBlockingHashMapTest {

    public static void main(String[] args) {
        final int numThreads = 4;
        final int numElements = 1000000;
//        Map map = new NonBlockingHashMap<>();
//        Map map = new ConcurrentHashMap<>();
        Map map = new NonBlockingIdentityHashMap<>();
        for (int i = 0; i < 3; i++) {
            // 使用JCTools的NonBlockingHashMap进行性能测试
            testPutHashMap(numThreads, numElements, map);

            // 使用JCTools的NonBlockingHashMap进行性能测试
            testGetHashMap(numThreads, numElements, map);
        }

    }

    private static void testPutHashMap(int numThreads, int numElements, Map<Integer, Integer> map) {
        long startTime = System.nanoTime();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int start = i * (numElements / numThreads);
            int end = (i + 1) * (numElements / numThreads);
            Thread thread = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    map.put(j, j);
                }
            });
            list.add(thread);
            thread.start();
        }

        // 等待所有线程完成
        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println(map.getClass().getSimpleName() + " put Time: " + duration + " ms");
    }

    private static void testGetHashMap(int numThreads, int numElements, Map<Integer, Integer> map) {
        long startTime = System.nanoTime();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            int start = i * (numElements / numThreads);
            int end = (i + 1) * (numElements / numThreads);
            Thread thread = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    map.get(j);
                }
            });
            list.add(thread);
            thread.start();
        }

        // 等待所有线程完成
        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println(map.getClass().getSimpleName() + " get Time: " + duration + " ms");
    }

}
