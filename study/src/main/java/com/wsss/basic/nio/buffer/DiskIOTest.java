package com.wsss.basic.nio.buffer;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.Random;

public class DiskIOTest {
    // 配置参数
    private static final int FILE_SIZE_MB = 100;             // 测试文件大小
    private static final int BLOCK_SIZE = 4 * 1024;          // 4KB块大小
    private static final int TOTAL_BLOCKS = FILE_SIZE_MB * 1024 * 1024 / BLOCK_SIZE;
    private static final int WARMUP_LOOPS = 3;               // 预热次数
    private static final int TEST_LOOPS = 5;                 // 正式测试次数
    private static final String TEST_FILE_PATH = "io_test.dat"; // 测试文件路径

    public static void main(String[] args) throws Exception {
        // 生成测试数据文件
        prepareTestFile();
        System.out.println("========== 预热 ==========");
        // 预热JVM
        warmup();

        // 执行测试
        System.out.println("========== 堆内存Buffer ==========");
        runTest(false);

        System.out.println("\n========== 直接内存Buffer ==========");
        runTest(true);

        // 清理测试文件
        new File(TEST_FILE_PATH).delete();
    }

    // 准备测试文件（预填充随机数据）
    private static void prepareTestFile() throws IOException {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) file.delete();

        try (FileChannel channel = new RandomAccessFile(file, "rw").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
            byte[] tempArray = new byte[BLOCK_SIZE];
            Random rand = new Random();

            for (int i = 0; i < TOTAL_BLOCKS; i++) {
                buffer.clear();
                rand.nextBytes(tempArray);
                buffer.put(tempArray);
                buffer.flip();
                channel.write(buffer);
            }
        }
    }

    // JVM预热
    private static void warmup() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
        File tempFile = File.createTempFile("warmup", null);

        for (int i = 0; i < WARMUP_LOOPS; i++) {
            testWrite(tempFile, buffer, true);  // 顺序写入
            testWrite(tempFile, buffer, false); // 随机写入
            testRead(tempFile, buffer, true);   // 顺序读取
            testRead(tempFile, buffer, false);  // 随机读取
        }
        tempFile.delete();
    }

    // 执行测试套件
    private static void runTest(boolean isDirect) throws IOException {
        ByteBuffer buffer = isDirect ?
                ByteBuffer.allocateDirect(BLOCK_SIZE) :
                ByteBuffer.allocate(BLOCK_SIZE);

        // 测试写入性能
        double seqWrite = testWrite(new File(TEST_FILE_PATH), buffer, true);
        double randWrite = testWrite(new File(TEST_FILE_PATH), buffer, false);

        // 测试读取性能
        double seqRead = testRead(new File(TEST_FILE_PATH), buffer, true);
        double randRead = testRead(new File(TEST_FILE_PATH), buffer, false);

        // 打印结果
        printResult("顺序写入", seqWrite);
        printResult("随机写入", randWrite);
        printResult("顺序读取", seqRead);
        printResult("随机读取", randRead);
    }

    // 写入测试核心方法
    private static double testWrite(File file, ByteBuffer buffer, boolean isSequential)
            throws IOException {
        long totalTime = 0;
        File tempFile = File.createTempFile("test", null);
        byte[] tempArray = new byte[BLOCK_SIZE];
        Random rand = new Random();
        rand.nextBytes(tempArray);

        for (int loop = 0; loop < TEST_LOOPS; loop++) {
            try (FileChannel channel = new RandomAccessFile(tempFile, "rw").getChannel()) {
                long start = System.nanoTime();

                for (int i = 0; i < TOTAL_BLOCKS; i++) {
                    buffer.clear();
                    buffer.put(tempArray);
                    buffer.flip();

                    if (isSequential) {
                        channel.write(buffer);
                    } else {
                        long position = rand.nextInt(TOTAL_BLOCKS) * (long) BLOCK_SIZE;
                        channel.write(buffer, position);
                    }
                    channel.force(false);
                }
                totalTime += System.nanoTime() - start;
            }
        }
        return calculateThroughput(FILE_SIZE_MB * TEST_LOOPS, totalTime);
    }

    // 读取测试核心方法
    private static double testRead(File file, ByteBuffer buffer, boolean isSequential)
            throws IOException {
        long totalTime = 0;
        Random rand = new Random();

        for (int loop = 0; loop < TEST_LOOPS; loop++) {
            try (FileChannel channel = new RandomAccessFile(file, "r").getChannel()) {
                long start = System.nanoTime();

                for (int i = 0; i < TOTAL_BLOCKS; i++) {
                    buffer.clear();
                    if (isSequential) {
                        channel.read(buffer);
                    } else {
                        long position = rand.nextInt(TOTAL_BLOCKS) * (long) BLOCK_SIZE;
                        channel.read(buffer, position);
                    }
//                    buffer.rewind(); // 模拟数据消费
                }
                totalTime += System.nanoTime() - start;
            }
        }
        return calculateThroughput(FILE_SIZE_MB * TEST_LOOPS, totalTime);
    }

    // 计算吞吐量 (MB/s)
    private static double calculateThroughput(int totalMB, long nanoTime) {
        return (totalMB * 1e9) / nanoTime;
    }

    // 结果打印格式化
    private static void printResult(String label, double throughput) {
        System.out.printf("%-8s | Throughput: %6.2f MB/s\n", label, throughput);
    }
}
