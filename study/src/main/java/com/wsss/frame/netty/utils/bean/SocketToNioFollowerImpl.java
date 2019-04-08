package com.wsss.frame.netty.utils.bean;

public class SocketToNioFollowerImpl implements SocketToNioFollower {
	@Override
	public void parseBytes(byte[] bytes) {
		String s = new String(bytes);
		System.out.println(s);
	}

}
