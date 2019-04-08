package com.wsss.frame.zookeeper.zkclient.autoupdate;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.InitializingBean;

public abstract class RegeditableUpdater implements InitializingBean {
	private ZkClient zk;
	private String localName;
	
	abstract String getRegeditName();
	
	abstract void invokeUpdate(WatchedEvent obj,String s);

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			regedit();
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		addListener(getLocalPath(), null);
	}
	
	public String getLocalServerIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		byte[] ipAddr = addr.getAddress();
		StringBuilder ipAddrStr = new StringBuilder();
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr.append(".");
			}
			ipAddrStr.append(ipAddr[i] & 0xFF);
		}
		System.out.println(("getLocalServerIP:" + ipAddrStr.toString()));
		return ipAddrStr.toString();
	}
	
	private void regedit() throws Exception {
		zk.createPersistent(getRegeditName(), true);
		String regeditName = getLocalPath();
		zk.createEphemeral(regeditName);
	}
	
	private void addListener(String path,Stat stat) {
		try {
			zk.subscribeDataChanges(path, new IZkDataListener() {
				@Override
				public void handleDataDeleted(String arg0) throws Exception {}
				
				@Override
				public void handleDataChange(String path, Object data) throws Exception {
					RegeditableUpdater.this.invokeUpdate(null,(String)data);
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getLocalPath() {
		if(localName == null) localName = "/" + System.currentTimeMillis();
		return getRegeditName() + localName;
	}
	
	public ZkClient getZk() {
		return zk;
	}

	public void setZk(ZkClient zk) {
		this.zk = zk;
	}
	
	
}
