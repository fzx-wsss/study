package com.wsss.basic.socket.study1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {
	private String host = "localhost";
	private int port = 8888;
	private Socket socket;
	private static int stopWay = 1; // 结束通信的方式
	private final int NATURAL_STOP = 1; // 自然结束
	private final int SUDDEN_STOP = 2; // 突然终止程序
	private final int SOCKET_STOP = 3; // 关闭Socket，再结束程序
	private final int OUTPUT_STOP = 4; // 关闭输出流，再结束程序

	public Sender() throws IOException {
		socket = new Socket(host, port);
	}

	public static void main(String args[]) throws Exception {
		
		//stopWay = 1;
		new Sender().send();
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	public void send() throws Exception {
		PrintWriter pw = getWriter(socket);
		BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		for (int i = 0; i < 20; i++) {
			String msg = "hello_" + i;
			pw.println(msg);
			System.out.println("send:" + msg);
			System.out.println(rd.readLine());
			Thread.sleep(2000);
			if (i == 2) { // 终止程序，结束通信
				if (stopWay == SUDDEN_STOP) {
					System.out.println("突然终止程序");
					System.exit(0);
				} else if (stopWay == SOCKET_STOP) {
					System.out.println("关闭Socket并终止程序");
					socket.close();
					break;
				} else if (stopWay == OUTPUT_STOP) {
					socket.shutdownOutput();

					System.out.println("关闭输出流并终止程序");
					break;
				}
			}
		}
		//System.out.println(rd.readLine());
		if (stopWay == NATURAL_STOP) {
			socket.close();
		}
	}
}

/****************************************************
 * 作者：孙卫琴 * 来源：<<Java网络编程精解>> * 技术支持网址：www.javathinker.org *
 ***************************************************/
