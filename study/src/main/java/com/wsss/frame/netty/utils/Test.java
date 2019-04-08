package com.wsss.frame.netty.utils;

import java.util.concurrent.TimeUnit;

import com.wsss.frame.netty.utils.bean.SocketToNioFollower;
import com.wsss.frame.netty.utils.bean.SocketToNioFollowerImpl;

public class Test {
	private static SocketToNioFollower socketToNioFollower = new SocketToNioFollowerImpl();
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<100000000;i++) {
			byte[] bytes = (i+"111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111").getBytes();
			NioSocketUtils.send(bytes, "127.0.0.1", 9090, socketToNioFollower);
		
		}
		TimeUnit.SECONDS.sleep(10000);
	}
}
