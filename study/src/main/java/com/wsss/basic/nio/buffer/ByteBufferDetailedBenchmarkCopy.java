package com.wsss.basic.nio.buffer;

import java.nio.ByteBuffer;

public class ByteBufferDetailedBenchmarkCopy {

    // 测试参数
    private static final int BUFFER_SIZE = 1024 * 1024;  // 1MB缓冲区
    private static final int INT_COUNT = BUFFER_SIZE / 4; // 可容纳的int数量（每个int占4字节）
    private static final int ITERATIONS = 1000;          // 操作迭代次数
    private static final int WARMUP_LOOPS = 10;            // 预热次数

    public static void main(String[] args) {
        // 预热JVM
        ByteBuffer heapBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        warmup(heapBuffer);
        warmup(directBuffer);

        // 堆内存测试
        System.out.println("========== Heap Buffer ==========");

//        runAllTests(heapBuffer);
        // Byte操作测试
        testByteByByteWrite(heapBuffer, "heapBuffer Byte逐笔写入");
        testByteByByteWrite(directBuffer, "directBuffer Byte逐笔写入");
        testByteBulkWrite(heapBuffer, "heapBuffer Byte批量写入");
        testByteBulkWrite(directBuffer, "directBuffer Byte批量写入");
        testByteByByteRead(heapBuffer, "heapBuffer Byte逐笔读取");
        testByteByByteRead(directBuffer, "directBuffer Byte逐笔读取");
        testByteBulkRead(heapBuffer, "heapBuffer Byte批量读取");
        testByteBulkRead(directBuffer, "directBuffer Byte批量读取");

        // Int操作测试
        testIntByIntWrite(heapBuffer, "heapBuffer Int逐笔写入");
        testIntByIntWrite(directBuffer, "directBuffer Int逐笔写入");
        testIntBulkWrite(heapBuffer, "heapBuffer Int批量写入");
        testIntBulkWrite(directBuffer, "directBuffer Int批量写入");
        testIntByIntRead(heapBuffer, "heapBuffer Int逐笔读取");
        testIntByIntRead(directBuffer, "directBuffer Int逐笔读取");
        testIntBulkRead(heapBuffer, "heapBuffer Int批量读取");
        testIntBulkRead(directBuffer, "directBuffer Int批量读取");

        // 直接内存测试
//        System.out.println("\n========== Direct Buffer ==========");

//        runAllTests(directBuffer);
        // Byte操作测试





        // Int操作测试




    }

    private static void runAllTests(ByteBuffer buffer) {
        testIntByIntWrite(buffer, "Int逐笔写入");
        testIntBulkWrite(buffer, "Int批量写入");
        testIntByIntRead(buffer, "Int逐笔读取");
        testIntBulkRead(buffer, "Int批量读取");
    }

    // 预热JVM
    private static void warmup(ByteBuffer buffer) {
        System.out.println("Warming up...");
        for (int i = 0; i < WARMUP_LOOPS; i++) {
            runAllTests(buffer);
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
