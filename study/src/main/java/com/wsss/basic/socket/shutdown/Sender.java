package com.wsss.basic.socket.shutdown;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {
	private String host = "localhost";
	private int port = 8989;
	private Socket socket;

	public Sender() throws IOException {
		socket = new Socket(host, port);
	}

	public static void main(String args[]) throws Exception {
		
		//stopWay = 1;
		new Sender().send();
	}


	public void send() throws Exception {
		OutputStream os = new BufferedOutputStream(socket.getOutputStream());
		// 由系统标准输入设备构造BufferedReader对象
		InputStream is = new BufferedInputStream(socket.getInputStream());
		
		os.write("SHUTDOWN".getBytes());
		os.flush();//没有flush，则数据不会发送出去
		//socket.shutdownOutput();//关闭输出流，对方可以收到-1
		os.close();//会关闭整个连接
		/*byte[] bytes = new byte[1024];
		int len;
		while((len = is.read(bytes)) != -1) {
			System.out.println(new String(bytes,0,len));
		}*/
		
		
		socket.close();
		//Thread.sleep(100000);
	}
}
