package com.wsss.frame.disruptor.work;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.wsss.frame.disruptor.base.MyEvent;
import com.wsss.frame.disruptor.base.MyEventFactory;
import com.wsss.frame.disruptor.base.MyEventHandler;

import java.util.concurrent.TimeUnit;

public class Worker3 {
    private Disruptor<MyEvent> queue;

    public Worker3(int no) {
        this.queue = new Disruptor<>(new MyEventFactory(), 1024 * 32, new ThreadFactoryBuilder().setNameFormat("account-partition-processor-queue-%d").build(), ProducerType.MULTI
                , new BlockingWaitStrategy());
        MyEventHandler[] handlers = new MyEventHandler[3];
        for (int i = 0; i < 3; i++) {
            handlers[i] = new MyEventHandler();
        }
//        this.queue.handleEventsWith(handlers).then(new MyEventHandler());
        this.queue.handleEventsWith(handlers).handleEventsWith(new MyEventHandler());

        this.queue.start();
    }

    public void produce(int i) {
        queue.publishEvent((event, sequence) -> event.init("task" + i, sequence));
    }


    public static void main(String[] args) throws Exception {
        Worker3 worker = new Worker3(1);
        for (int i = 0; i < 10; i++) {
            worker.produce(i);
            TimeUnit.SECONDS.sleep(3);
        }

        TimeUnit.MINUTES.sleep(100);
    }
}
