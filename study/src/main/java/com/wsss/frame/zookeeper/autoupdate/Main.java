package com.wsss.frame.zookeeper.autoupdate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Main {
	public static void main(String[] args) throws Exception {
		final CountDownLatch latch = new CountDownLatch(1);
		ZooKeeper zk = new ZooKeeper("localhost:2181", 2000, new Watcher() {
			// 监控所有被触发的事件
			public void process(WatchedEvent event) {
				System.out.println(">>>>>>>>>>>>>>已经触发了" + event.getType() + "事件！");
				System.out.println(">>>>>>>>>>>>>>" + event.getState());
				latch.countDown();
			}
		});
		latch.await();

		/*
		 * DefaultRegeditableUpdater updater = new DefaultRegeditableUpdater();
		 * updater.setZk(zk); updater.afterPropertiesSet();
		 * 
		 * DefaultRegeditableUpdater2 updater2 = new
		 * DefaultRegeditableUpdater2(); updater2.setZk(zk);
		 * updater2.afterPropertiesSet();
		 */

		System.out.println("设置完成");

		for (int i = 0; i < 100000; i++) {
			// zk.setData(updater.getRegeditName(), "1".getBytes(), -1);
			System.out.println("---------------------------------------------------");
			Thread.sleep(3000);
			try {
				List<String> list = zk.getChildren("/test/one/user", false);
				for (String child : list) {
					System.out.println(("节点为：" + child));
					zk.setData("/test/one/user/" + child, String.valueOf(i).getBytes(), -1);
				}
			} catch (KeeperException.NoNodeException e) {
				throw e;
			}
		}

		System.in.read();
	}

}
