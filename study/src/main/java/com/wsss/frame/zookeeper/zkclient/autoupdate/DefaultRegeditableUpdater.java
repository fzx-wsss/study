package com.wsss.frame.zookeeper.zkclient.autoupdate;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.ZkClient;
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
		ZkClient zk = new ZkClient("127.0.0.1");
		DefaultRegeditableUpdater up = new DefaultRegeditableUpdater();
		up.setZk(zk);
		up.afterPropertiesSet();
		System.out.println("DefaultRegeditableUpdater start");
		System.in.read();
	}

}
