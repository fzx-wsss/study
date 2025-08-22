package com.wsss.basic.safe.point;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 添加JVM打印安全点日志参数-XX:+PrintSafepointStatistics后再执行上面的实例代码
 * 加上 -XX:+SafepointTimeout 和-XX:SafepointTimeoutDelay=2000 参数后执行代码可以进一步看等待哪两个线程进入安全点。
 * Java -XX:+UnlockDiagnosticVMOptions -XX:+PrintFlagsFinal 2>&1 | grep Safepoint 命令查看 JVM 关于安全点的默认参数
 */
public class SafePointTest {
    public static AtomicInteger counter = new AtomicInteger(0);
    public static BigDecimal counter2 = BigDecimal.ZERO;

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        Runnable runnable = () -> {
            System.out.println(interval(startTime) + "ms后，" + Thread.currentThread().getName() + "子线程开始运行");
            for (int i = 0; i < 300000000; i++) {
                counter.getAndAdd(1);
//                getAndAdd();
//                counter2 = counter2.add(BigDecimal.ONE);
//                someMethod();
            }

            System.out.println(interval(startTime) + "ms后，" + Thread.currentThread().getName() + "子线程结束运行, counter=" + counter);
        };

        Thread t1 = new Thread(runnable, "zz-t1");
        Thread t2 = new Thread(runnable, "zz-t2");
        t1.start();
        t2.start();

        System.out.println(interval(startTime) + "ms后，主线程开始sleep.");
        Thread.sleep(1000L);
        System.out.println(interval(startTime) + "ms后，主线程结束sleep.");
        System.out.println(interval(startTime) + "ms后，主线程结束，counter:" + counter);
        // 主线程几秒后会结束？
    }

    private static void getAndAdd() {
        counter.getAndAdd(1);
    }

    private static void someMethod() {
        System.nanoTime();
    }

    private static long interval(Long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}
