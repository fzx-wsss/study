package com.wsss.frame.disruptor.work;

import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import com.wsss.frame.disruptor.base.MyEvent;
import com.wsss.frame.disruptor.base.MyEventFactory;
import com.wsss.frame.disruptor.base.MyWorkHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Work4 {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        RingBuffer<MyEvent> ringBuffer = RingBuffer.create(ProducerType.SINGLE,new MyEventFactory(),1024,new YieldingWaitStrategy());
        WorkerPool<MyEvent> workerPool = new WorkerPool<MyEvent>(ringBuffer,ringBuffer.newBarrier(),new IgnoreExceptionHandler(),new MyWorkHandler());

        workerPool.start(executor);

        //-------------生产数据
        for(int i = 0 ; i < 30 ; i++){

            long sequence = ringBuffer.next();

            MyEvent order = ringBuffer.get(sequence);
            order.setSequence(i);

            ringBuffer.publish(sequence);

            System.out.println(Thread.currentThread().getName() + " 生产者发布一条数据:" + sequence + " 订单ID：" + i);
        }

        Thread.sleep(100000);

        workerPool.halt();
        executor.shutdown();

    }
}
