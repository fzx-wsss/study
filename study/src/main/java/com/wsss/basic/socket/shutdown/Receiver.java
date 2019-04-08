package com.wsss.basic.socket.shutdown;

import java.io.*;
import java.net.*;

public class Receiver {
	private int port = 8888;
	private ServerSocket serverSocket;

	public Receiver() throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("服务器已经启动");
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	public void receive() throws Exception {
		Socket socket = null;
		socket = serverSocket.accept();
		OutputStream os = new BufferedOutputStream(socket.getOutputStream());
		// 由系统标准输入设备构造BufferedReader对象
		InputStream is = new BufferedInputStream(socket.getInputStream());
		
		byte[] bytes = new byte[1024];
		int len;
		while((len = is.read(bytes)) != -1) {
			System.out.println(new String(bytes,0,len));
		}
		
		os.write("服务器返回".getBytes());
		os.flush();
		//socket.shutdownOutput();
		socket.close();
		//Thread.sleep(100000);
	}

	public static void main(String args[]) throws Exception {
		
		//stopWay = 1;
		new Receiver().receive();
	}
}


