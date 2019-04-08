package com.wsss.basic.nio.timeServer;

public class TimeClient {
	public static void main(String[] args) {
		new Thread(new TimeClientHandle("127.0.0.1", 9999)).start();
	}
}
