package com.wsss.basic.nio.aio.timeServer.server;

public class TimeServer {
	public static void main(String[] args) {
		AsyncTimeServerHandler serverHandler = new AsyncTimeServerHandler(9090);
		new Thread(serverHandler).start();
	}
}
