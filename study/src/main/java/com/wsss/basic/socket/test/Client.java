package com.wsss.basic.socket.test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws Exception {
		for(int i=0;i<1000;i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// TODO Auto-generated method stub
						Socket s1 = new Socket("localhost",8443);
						byte[] bytes = new byte[1024];
						int len = s1.getInputStream().read(bytes);
						System.out.println(new String(bytes,0,len));
						//Thread.sleep(10000);
						s1.close();
						System.out.println("第一次连接成功");
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
			
		}
		Thread.sleep(100000);
	}
}
