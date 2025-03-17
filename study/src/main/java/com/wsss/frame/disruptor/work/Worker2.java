package com.wsss.frame.disruptor.work;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.wsss.frame.disruptor.base.*;

import java.util.concurrent.TimeUnit;

public class Worker2 {
    private Disruptor<MyEvent> queue;

    public Worker2(int no) {
        this.queue = new Disruptor<>(new MyEventFactory(), 1024 * 32, new ThreadFactoryBuilder().setNameFormat("account-partition-processor-queue-%d").build(), ProducerType.MULTI
                , new BlockingWaitStrategy());
        MyEventHandler[] handlers = new MyEventHandler[3];
        for (int i = 0; i < 3; i++) {
            handlers[i] = new MyEventHandler();
        }
        this.queue.handleEventsWith(handlers);

        this.queue.start();
    }

    public void produce(int i) {
        queue.publishEvent((event, sequence) -> event.init(new MyData(i), sequence));
    }


    public static void main(String[] args) throws Exception {
        Worker2 worker = new Worker2(1);
        for (int i = 0; i < 10; i++) {
            worker.produce(i);
            TimeUnit.SECONDS.sleep(1);
        }

        TimeUnit.MINUTES.sleep(100);
    }
}
