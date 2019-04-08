package com.wsss.frame.zookeeper.autoupdate;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Main2 {
	public static void main(String[] args) throws Exception {
		final CountDownLatch latch = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, new Watcher() {
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				System.out.println(">>>>>>>>>>>>>>已经触发了" + event.getType()
						+ "事件！");
				System.out.println(">>>>>>>>>>>>>>" + event.getState());
				latch.countDown();
			}
		});
		latch.await();
		
		DefaultRegeditableUpdater updater = new DefaultRegeditableUpdater();
		
		System.out.println("设置完成");
		
		for(int i=0;i<1;i++) {
			zk.setData(updater.getRegeditName(), "1".getBytes(), -1);
			Thread.sleep(10);
		}
		
		
		System.in.read();
	}
}
