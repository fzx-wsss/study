package com.wsss.basic.test;

public class TimeMethodBenchmark {
    private static final int WARMUP_ITERATIONS = 10_000;
    private static final int MEASUREMENT_ITERATIONS = 100_000;
    private static final int OPERATIONS_PER_ITERATION = 1000;

    public static void main(String[] args) {
        // 预热JVM
        System.out.println("开始预热...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            testCurrentTimeMillis();
            testNanoTime();
        }
        System.out.println("预热完成\n");

        // 测试System.currentTimeMillis()
        long totalMillisTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            totalMillisTime += testCurrentTimeMillis();
        }
        double avgMillisTime = (double) totalMillisTime / MEASUREMENT_ITERATIONS;
        System.out.printf("System.currentTimeMillis() 平均耗时: %.3f ns%n", avgMillisTime);

        // 测试System.nanoTime()
        long totalNanoTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            totalNanoTime += testNanoTime();
        }
        double avgNanoTime = (double) totalNanoTime / MEASUREMENT_ITERATIONS;
        System.out.printf("System.nanoTime() 平均耗时: %.3f ns%n", avgNanoTime);

        // 比较结果
        System.out.printf("\nSystem.nanoTime() 比 System.currentTimeMillis() %.2f%% %s%n",
                Math.abs(avgNanoTime - avgMillisTime) / avgMillisTime * 100,
                avgNanoTime < avgMillisTime ? "快" : "慢");
    }

    private static long testCurrentTimeMillis() {
        long start = System.nanoTime();
        for (int i = 0; i < OPERATIONS_PER_ITERATION; i++) {
            System.currentTimeMillis();
        }
        return System.nanoTime() - start;
    }

    private static long testNanoTime() {
        long start = System.nanoTime();
        for (int i = 0; i < OPERATIONS_PER_ITERATION; i++) {
            long l = System.nanoTime() / 1000000L;
        }
        return System.nanoTime() - start;
    }
}
