package com.wsss.basic.nio.timeServer;

public class TimeServer {
	public static void main(String[] args) {
		MultiplexerTimeServer server = new MultiplexerTimeServer(9999);
		new Thread(server).start();
	}
	
}
