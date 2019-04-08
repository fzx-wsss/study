package com.wsss.basic.socket.study1;

import java.io.*;
import java.net.*;

public class SimpleServer {
	public static void main(String args[]) throws Exception {
		ServerSocket serverSocket = new ServerSocket(9000, 10); // 连接请求队列的长度为2
		while (true) {
			Socket s1 = serverSocket.accept();
			OutputStream ot = s1.getOutputStream();
			ot.write("SUCCESS".getBytes());
			ot.flush();
			Thread.sleep(1000);
			ot.close();
			Thread.sleep(1000);
			s1.close();
			// serverSocket.close();
		}
		// Thread.sleep(360000); //睡眠6分钟
	}
}

/****************************************************
 * 作者：孙卫琴 * 来源：<<Java网络编程精解>> * 技术支持网址：www.javathinker.org *
 ***************************************************/
