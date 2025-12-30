package com.wsss.basic.nio.buffer;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ByteBufferTest {
    private static final int BUFFER_SIZE = 1024 * 1024;  // 1MB缓冲区
    private static final int INT_COUNT = BUFFER_SIZE / 4; // 可容纳的int数量（每个int占4字节）
    private static final int ITERATIONS = 5000;          // 操作迭代次数

    public static void main(String[] args) {
        ByteBuffer heapBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        byte[] data = new byte[BUFFER_SIZE];
        ThreadLocalRandom.current().nextBytes(data);
        heapBuffer.put(data);
        directBuffer.put(data);

        System.out.println("预热开始");
        for (int i = 0; i < 10; i++) {
            testIntByIntRead(directBuffer);
            testIntByIntRead(heapBuffer);
            testIntByIntWrite(heapBuffer);
            testIntByIntWrite(directBuffer);
            testIntByIntRead(data);
            testIntByIntWrite(data);
        }
        System.out.println("预热结束");

        long time;
        time = testIntByIntRead(directBuffer);
        System.out.println("Direct Buffer read" + ":" + TimeUnit.NANOSECONDS.toMillis(time));
        time = testIntByIntRead(heapBuffer);
        System.out.println("Heap Buffer read" + ":" + TimeUnit.NANOSECONDS.toMillis(time));
        time = testIntByIntRead(data);
        System.out.println("Array read" + ":" + TimeUnit.NANOSECONDS.toMillis(time));

        time = testIntByIntWrite(heapBuffer);
        System.out.println("Heap Buffer write" + ":" + TimeUnit.NANOSECONDS.toMillis(time));
        time = testIntByIntWrite(directBuffer);
        System.out.println("Direct Buffer write" + ":" + TimeUnit.NANOSECONDS.toMillis(time));
        time = testIntByIntWrite(data);
        System.out.println("Array write" + ":" + TimeUnit.NANOSECONDS.toMillis(time));
    }

    private static long testIntByIntWrite(ByteBuffer buffer) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            for (int j = 0; j < INT_COUNT; j++) {
                buffer.putInt(j);  // 逐int写入
            }
        }
        return System.nanoTime() - start;
    }

    private static long testIntByIntRead(ByteBuffer buffer) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.flip();
            for (int j = 0; j < INT_COUNT; j++) {
                buffer.getInt();  // 逐int读取
            }
        }
        return System.nanoTime() - start;
    }

    private static long testByteByByteWrite(ByteBuffer buffer) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.clear();
            for (int j = 0; j < BUFFER_SIZE; j++) {
                buffer.put((byte) (j % 127));  // 逐字节写入
            }
        }
        return System.nanoTime() - start;

    }

    private static long testByteByByteRead(ByteBuffer buffer) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            buffer.flip();
            for (int j = 0; j < BUFFER_SIZE; j++) {
                buffer.get();  // 逐字节读取
            }
        }
        return System.nanoTime() - start;
    }
    private static long testByteByByteRead(byte[] buffer) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            for (int j = 0; j < BUFFER_SIZE; j++) {
                byte b = buffer[j];  // 逐字节读取
            }
        }
        return System.nanoTime() - start;
    }

    private static long testByteByByteWrite(byte[] buffer) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            for (int j = 0; j < BUFFER_SIZE; j++) {
                buffer[j] = (byte) (j % 127); // 逐字节写入
            }
        }
        return System.nanoTime() - start;
    }

    private static long testIntByIntWrite(byte[] buffer) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            for (int j = 0; j < INT_COUNT; j++) {
                intToBytesLittleEndian(j,buffer,j*4);  // 逐int写入
            }
        }
        return System.nanoTime() - start;
    }

    private static long testIntByIntRead(byte[] buffer) {
        long start = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            for (int j = 0; j < INT_COUNT; j++) {
                bytesToIntLittleEndian(buffer,j*4);  // 逐int读取
            }
        }
        return System.nanoTime() - start;
    }

    /**
     * 将int数字转换为4字节的byte数组（小端序）
     * @param value 要转换的int值
     * @return 4字节的byte数组
     */
    private static  byte[] intToBytesLittleEndian(int value,byte[] result,int index) {
        result[index] = (byte) value;
        result[index+1] = (byte) (value >> 8);
        result[index+2] = (byte) (value >> 16);
        result[index+3] = (byte) (value >> 24);
        return result;
    }

    /**
     * 将4字节的byte数组转换为int数字（小端序）
     * @param bytes 4字节的byte数组
     * @return 转换后的int值
     */
    private static int bytesToIntLittleEndian(byte[] bytes,int index) {
        return (bytes[index] & 0xFF) |
                ((bytes[index+1] & 0xFF) << 8) |
                ((bytes[index+2] & 0xFF) << 16) |
                ((bytes[index+3] & 0xFF) << 24);
    }
}
