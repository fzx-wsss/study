package com.wsss.basic.socket.test;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActiveServer {
	public static ExecutorService executor = Executors.newFixedThreadPool(20);
	public static void main(String[] args) throws Exception {
		
		final ServerSocket serverSocket = new ServerSocket(9000); // 连接请求队列的长度为2
		
		while (true) {
			final Socket s1 = serverSocket.accept();
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						OutputStream ot = s1.getOutputStream();
						System.out.println("接收");
						ot.write("SUCCESS".getBytes());
						ot.flush();
						//Thread.sleep(10000);
						ot.close();
						//Thread.sleep(1000);
						s1.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			// serverSocket.close();
		}
	}
}
