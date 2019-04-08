package com.wsss.basic.socket.study3.block;

import java.net.*;
import java.nio.channels.*;
import java.nio.*;
import java.io.*;
import java.nio.charset.*;

public class EchoClient {
	private SocketChannel socketChannel = null;
	BufferedReader br = null;
	PrintWriter pw = null;
	public EchoClient() throws IOException {
		socketChannel = SocketChannel.open();
		InetAddress ia = InetAddress.getLocalHost();
		InetSocketAddress isa = new InetSocketAddress(ia, 8443);
		socketChannel.connect(isa);
		System.out.println("与服务器的连接建立成功");
	}

	public static void main(String args[]) throws Exception {
		EchoClient client = new EchoClient();
		System.out.println(System.currentTimeMillis());
		//Thread.sleep(10);
		client.talk("bye");
		client.talk();
		client.socketChannel.close();
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public void talk() throws IOException {
		try {
			//BufferedReader br = getReader(socketChannel.socket());
			//PrintWriter pw = getWriter(socketChannel.socket());
			BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			while ((msg = localReader.readLine()) != null) {
				pw.println(msg);
				System.out.println(br.readLine());

				if (msg.equals("bye"))
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socketChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void talk(String msg) throws IOException {
		try {
			br = getReader(socketChannel.socket());
			pw = getWriter(socketChannel.socket());
			pw.println(msg);
			//System.out.println(br.readLine());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

/****************************************************
 * 作者：孙卫琴 * 来源：<<Java网络编程精解>> * 技术支持网址：www.javathinker.org *
 ***************************************************/
