package com.wsss.basic.nio.buffer;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class ByteBufferDetailedBenchmark {

    // 测试参数
    private static final int BUFFER_SIZE = 1024 * 1024;  // 1MB缓冲区
    private static final int INT_COUNT = BUFFER_SIZE / 4; // 可容纳的int数量（每个int占4字节）
    private static final int ITERATIONS = 1000;          // 操作迭代次数
    private static final int WARMUP_LOOPS = 100;            // 预热次数

    public static void main(String[] args) {
        // 预热JVM
        warmup();

        // 堆内存测试
        System.out.println("========== Heap Buffer ==========");
        ByteBuffer heapBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        runAllTests(heapBuffer);

        // 直接内存测试
        System.out.println("\n========== Direct Buffer ==========");
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        runAllTests(directBuffer);
    }

    private static void runAllTests(ByteBuffer buffer) {
        // Byte操作测试
        testByteByByteWrite(buffer, "Byte逐笔写入");
        testByteBulkWrite(buffer, "Byte批量写入");
        testByteByByteRead(buffer, "Byte逐笔读取");
        testByteBulkRead(buffer, "Byte批量读取");

        // Int操作测试
        testIntByIntWrite(buffer, "Int逐笔写入");
        testIntBulkWrite(buffer, "Int批量写入");
        testIntByIntRead(buffer, "Int逐笔读取");
        testIntBulkRead(buffer, "Int批量读取");
    }

    // 预热JVM
    private static void warmup() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        System.out.println("Warming up...");
        for (int i = 0; i < WARMUP_LOOPS; i++) {
            testByteBulkWrite(buffer, "Warmup");
            testIntBulkRead(buffer, "Warmup");
        }
        System.out.println("Warmup completed.\n");
    }

    // ----- Byte操作测试 -----
    private static void testByteByByteWrite(ByteBuffer buffer, String label) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            for (int j = 0; j < BUFFER_SIZE; j++) {
                buffer.put((byte) (j % 127));  // 逐字节写入
            }
        }
        printResult(label, System.nanoTime() - start, BUFFER_SIZE * ITERATIONS);
    }

    private static void testByteBulkWrite(ByteBuffer buffer, String label) {
        byte[] data = new byte[BUFFER_SIZE];
        for (int i = 0; i < data.length; i++) data[i] = (byte) (i % 127);

        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            buffer.put(data);  // 一次性批量写入
        }
        printResult(label, System.nanoTime() - start, BUFFER_SIZE * ITERATIONS);
    }

    private static void testByteByByteRead(ByteBuffer buffer, String label) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            for (int j = 0; j < BUFFER_SIZE; j++) {
                buffer.get();  // 逐字节读取
            }
        }
        printResult(label, System.nanoTime() - start, BUFFER_SIZE * ITERATIONS);
    }

    private static void testByteBulkRead(ByteBuffer buffer, String label) {
        byte[] dest = new byte[BUFFER_SIZE];
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            buffer.get(dest);  // 一次性批量读取
        }
        printResult(label, System.nanoTime() - start, BUFFER_SIZE * ITERATIONS);
    }

    // ----- Int操作测试 -----
    private static void testIntByIntWrite(ByteBuffer buffer, String label) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            for (int j = 0; j < INT_COUNT; j++) {
                buffer.putInt(j);  // 逐int写入
            }
        }
        printResult(label, System.nanoTime() - start, INT_COUNT * 4L * ITERATIONS);
    }

    private static void testIntBulkWrite(ByteBuffer buffer, String label) {
        int[] data = new int[INT_COUNT];
        for (int i = 0; i < data.length; i++) data[i] = i;

        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            buffer.asIntBuffer().put(data);  // 通过IntBuffer批量写入
        }
        printResult(label, System.nanoTime() - start, INT_COUNT * 4L * ITERATIONS);
    }

    private static void testIntByIntRead(ByteBuffer buffer, String label) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            for (int j = 0; j < INT_COUNT; j++) {
                buffer.getInt();  // 逐int读取
            }
        }
        printResult(label, System.nanoTime() - start, INT_COUNT * 4L * ITERATIONS);
    }

    private static void testIntBulkRead(ByteBuffer buffer, String label) {
        int[] dest = new int[INT_COUNT];
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            buffer.asIntBuffer().get(dest);  // 通过IntBuffer批量读取
        }
        printResult(label, System.nanoTime() - start, INT_COUNT * 4L * ITERATIONS);
    }

    // 结果输出（吞吐量：MB/s）
    private static void printResult(String label, long durationNs, long totalBytes) {
        double throughput = (totalBytes / (1024.0 * 1024)) / (durationNs / 1e6);
        System.out.printf("%-14s | Time: %6.2f ms | Throughput: %6.2f MB/ms\n",
                label, durationNs / 1e6, throughput);
    }
}
