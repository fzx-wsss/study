package com.wsss.frame.disruptor.work;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.wsss.frame.disruptor.base.MyEvent;
import com.wsss.frame.disruptor.base.MyEventFactory;
import com.wsss.frame.disruptor.base.MyEventHandler;
import com.wsss.frame.disruptor.base.MyWorkHandler;

import java.util.concurrent.TimeUnit;

public class Worker {
    private Disruptor<MyEvent> queue;

    public Worker(int no) {
        this.queue = new Disruptor<>(new MyEventFactory(), 1024 * 32, new ThreadFactoryBuilder().setNameFormat("account-partition-processor-queue-%d").build(), ProducerType.MULTI
                , new BlockingWaitStrategy());
        MyWorkHandler[] handlers = new MyWorkHandler[3];
        for (int i = 0; i < 3; i++) {
            handlers[i] = new MyWorkHandler();
        }
        this.queue.handleEventsWithWorkerPool(handlers);

        this.queue.start();
    }

    public void produce(int i) {
        queue.publishEvent((event, sequence) -> event.init("task" + i, sequence));
    }


    public static void main(String[] args) throws Exception {
        Worker worker = new Worker(1);
        for (int i = 0; i < 10; i++) {
            worker.produce(i);
//            TimeUnit.SECONDS.sleep(1);
        }

        TimeUnit.MINUTES.sleep(100);
    }
}
