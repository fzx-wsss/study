package com.wsss.frame.zookeeper.autoupdate;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DefaultRegeditableUpdater extends RegeditableUpdater {

	@Override
	public String getRegeditName() {
		return "/test/one/user";
	}

	@Override
	public void invokeUpdate(WatchedEvent obj,String s) {
		try {
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
		DefaultRegeditableUpdater up = new DefaultRegeditableUpdater();
		up.setZk(zk);
		up.afterPropertiesSet();
		System.out.println("DefaultRegeditableUpdater start");
		System.in.read();
	}

}
