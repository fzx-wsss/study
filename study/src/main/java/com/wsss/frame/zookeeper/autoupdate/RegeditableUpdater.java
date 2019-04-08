package com.wsss.frame.zookeeper.autoupdate;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.InitializingBean;

public abstract class RegeditableUpdater implements InitializingBean {
	private ZooKeeper zk;
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
		
		getData(getLocalPath(), null);
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
		String regeditName = getLocalPath();
		String[] paths = regeditName.split("/");
		
		StringBuilder sb =new StringBuilder();
		for(String pathStr : paths) {
			if(StringUtils.isEmpty(pathStr)) continue;
			sb.append("/").append(pathStr);
			Stat stat = zk.exists(sb.toString(), false);
			if(null == stat) zk.create(sb.toString(), null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		zk.delete(regeditName, -1);
		zk.create(sb.toString(), null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
	}
	
	private void reRegedit(WatchedEvent arg0) {
		String s =new String(getData(getLocalPath(), true, null));
		invokeUpdate(arg0,s);
		getData(getLocalPath(),null);
	}
	
	private byte[] getData(String path,boolean watcher,Stat stat) {
		try {
			return zk.getData(path, watcher, stat);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private byte[] getData(String path,Stat stat) {
		try {
			final RegeditableUpdater updater = this;
			return zk.getData(path, new Watcher() {
				@Override
				public void process(WatchedEvent arg0) {
					updater.reRegedit(arg0);
				}
			}, null);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getLocalPath() {
		if(localName == null) localName = "/" + System.currentTimeMillis();
		return getRegeditName() + localName;
	}
	
	public ZooKeeper getZk() {
		return zk;
	}

	public void setZk(ZooKeeper zk) {
		this.zk = zk;
	}
	
	
}
