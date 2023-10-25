package com.wsss.frame.jctools;

import org.jctools.queues.SpscArrayQueue;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class SpscArrayQueueTest {
    public static void main(String[] args) {
        // 定义队列容量
        final int capacity = 1000000;
        // 创建SpscArrayQueue
        Queue<Integer> queue = new SpscArrayQueue<>(10000);
//        Queue<Integer> queue = new BlockingArrayQueue<>(10000);

        // 生产者线程
        Thread producer = new Thread(() -> {
            long startTime = System.nanoTime();
            for (int i = 0; i < capacity; i++) {
                while (!queue.offer(i)) {
                    // 如果队列已满，等待
                    Thread.yield();
                }
            }
            long endTime = System.nanoTime();
            System.out.println("Producer time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " ms");
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            long startTime = System.nanoTime();
            int sum = 0;
            for (int i = 0; i < capacity; i++) {
                while (queue.isEmpty()) {
                    // 如果队列为空，等待
                    Thread.yield();
                }
                queue.poll();
                sum += 1;
            }
            long endTime = System.nanoTime();
            System.out.println("Consumer time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " ms");
            System.out.println("Sum of elements: " + sum);
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}