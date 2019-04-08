package com.wsss.basic.socket.study1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Test {
	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket("angel",80);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			System.out.println("unknow");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("io");
		}
		//socket.setTrafficClass(1);
		System.out.println(socket.getRemoteSocketAddress());
		
	}
}
