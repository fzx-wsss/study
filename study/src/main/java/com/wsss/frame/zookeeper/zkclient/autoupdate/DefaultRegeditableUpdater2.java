package com.wsss.frame.zookeeper.zkclient.autoupdate;

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class DefaultRegeditableUpdater2 extends RegeditableUpdater {

	@Override
	public String getRegeditName() {
		return "/test/one/user";
	}

	@Override
	public void invokeUpdate(WatchedEvent obj,String s) {
		try {
			System.out.println("2:" + s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		ZkClient zk = new ZkClient("127.0.0.1");
		DefaultRegeditableUpdater2 up = new DefaultRegeditableUpdater2();
		up.setZk(zk);
		up.afterPropertiesSet();
		System.out.println("DefaultRegeditableUpdater2 start");
		System.in.read();
	}

}
