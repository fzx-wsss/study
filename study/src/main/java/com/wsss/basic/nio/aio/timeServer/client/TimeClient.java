package com.wsss.basic.nio.aio.timeServer.client;

public class TimeClient {
	public static void main(String[] args) {
		new Thread(new AsyncTimeClientHandler("127.0.0.1", 9090)).start();
	}
}
