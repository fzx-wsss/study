package com.wsss.basic.nio.buffer;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class MemoryPerformanceTest {
    private static final int FILE_SIZE = 100 * 1024 * 1024; // 100MB
    private static final int BUFFER_SIZE = 4 * 1024; // 4KB buffer size
    private static final String FILE_PATH = "testfile.dat";

    public static void main(String[] args) throws Exception {
        System.out.println("Warming up JVM...");
        warmUp();

        System.out.println("Testing Heap ByteBuffer");
        testMemoryAccess(ByteBuffer.allocate(BUFFER_SIZE), "Heap");

        System.out.println("\nTesting Direct ByteBuffer");
        testMemoryAccess(ByteBuffer.allocateDirect(BUFFER_SIZE), "Direct");
    }

    private static void warmUp() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        for (int i = 0; i < 5; i++) { // Run a few iterations to warm up
            testMemoryAccess(buffer, "Warmup");
        }
        System.gc(); // Suggest garbage collection after warm-up
    }

    private static void testMemoryAccess(ByteBuffer buffer, String type) throws Exception {
        long startTime, endTime;

        // Sequential Write
        startTime = System.nanoTime();
        sequentialWrite(buffer);
        endTime = System.nanoTime();
        printResults("Sequential Write", type, startTime, endTime);

        // Sequential Read
        startTime = System.nanoTime();
        sequentialRead(buffer);
        endTime = System.nanoTime();
        printResults("Sequential Read", type, startTime, endTime);

        // Random Write
        startTime = System.nanoTime();
        randomWrite(buffer);
        endTime = System.nanoTime();
        printResults("Random Write", type, startTime, endTime);

        // Random Read
        startTime = System.nanoTime();
        randomRead(buffer);
        endTime = System.nanoTime();
        printResults("Random Read", type, startTime, endTime);
    }

    private static void sequentialWrite(ByteBuffer buffer) throws Exception {
        try (FileChannel channel = new RandomAccessFile(FILE_PATH, "rw").getChannel()) {
            buffer.clear();
            for (int i = 0; i < FILE_SIZE / BUFFER_SIZE; i++) {
                buffer.put(generateData(BUFFER_SIZE));
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
            }
        }
    }

    private static void sequentialRead(ByteBuffer buffer) throws Exception {
        try (FileChannel channel = new RandomAccessFile(FILE_PATH, "r").getChannel()) {
            buffer.clear();
            while (channel.read(buffer) != -1) {
                buffer.flip();
                buffer.clear();
            }
        }
    }

    private static void randomWrite(ByteBuffer buffer) throws Exception {
        try (FileChannel channel = new RandomAccessFile(FILE_PATH, "rw").getChannel()) {
            buffer.clear();
            Random random = new Random();
            for (int i = 0; i < FILE_SIZE / BUFFER_SIZE; i++) {
                buffer.put(generateData(BUFFER_SIZE));
                buffer.flip();
                channel.position(random.nextInt(FILE_SIZE - BUFFER_SIZE));
                channel.write(buffer);
                buffer.clear();
            }
        }
    }

    private static void randomRead(ByteBuffer buffer) throws Exception {
        try (FileChannel channel = new RandomAccessFile(FILE_PATH, "r").getChannel()) {
            buffer.clear();
            Random random = new Random();
            for (int i = 0; i < FILE_SIZE / BUFFER_SIZE; i++) {
                channel.position(random.nextInt(FILE_SIZE - BUFFER_SIZE));
                channel.read(buffer);
                buffer.flip();
                buffer.clear();
            }
        }
    }

    private static byte[] generateData(int size) {
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = 'x';
        }
        return data;
    }

    private static void printResults(String operation, String type, long startTime, long endTime) {
        double elapsedTime = (endTime - startTime) / 1e9; // Convert to seconds
        double throughput = FILE_SIZE / (1024.0 * 1024.0) / elapsedTime; // MB/s
        System.out.printf("%s (%s): %.3f seconds, %.2f MB/s%n", operation, type, elapsedTime, throughput);
    }
}
