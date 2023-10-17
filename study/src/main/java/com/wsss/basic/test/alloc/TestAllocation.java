package com.wsss.basic.test.alloc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * 测试逃逸分析和标量替换
 */
public class TestAllocation {
    public static void main(String[] args) throws Exception {
        //-XX:+PrintGC -XX:+PrintGCDetails gc日志
        // 虚拟机关闭指针逃逸分析
        //-XX:-DoEscapeAnalysis
        // 虚拟机关闭标量替换
        // -XX:-EliminateAllocations
        TimeContext one = null;
        TimeContext[] old = new TimeContext[1];
        List<TimeContext> depthQueue = new ArrayList<>(1);
//        Thread t = new Thread(()->{
//            while (true) {
//                depthQueue.size();
//                try {
//                    TimeUnit.MILLISECONDS.sleep(100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        t.start();
        long start = System.currentTimeMillis();
        for(int i=0;i<100000000;i++) {
            TimeContext context = TimeContext.timer("123");
            one = context;
            // 以下操作不会再进行标量替换
//            depthQueue.add(context);
//            depthQueue.remove(context);
//            Arrays.asList(context);
//            old[0] = context;
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


}
