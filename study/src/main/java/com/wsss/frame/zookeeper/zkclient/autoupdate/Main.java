package com.wsss.frame.zookeeper.zkclient.autoupdate;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Main {
	public static void main(String[] args) throws Exception {
		ZkClient zk = new ZkClient("127.0.0.1");

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
				List<String> list = zk.getChildren("/test/one/user");
				for (String child : list) {
					System.out.println(("节点为：" + child));
					zk.writeData("/test/one/user/" + child, String.valueOf(i));
				}
			} catch (Exception e) {
				throw e;
			}
		}

		System.in.read();
	}

}
