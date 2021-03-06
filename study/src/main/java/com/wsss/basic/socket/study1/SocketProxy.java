package com.wsss.basic.socket.study1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class SocketProxy {
	public static void main(String[] args) throws Exception {
		Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(
				"127.0.0.1", 8989));
		Socket socket = new Socket(proxy);
		socket.connect(new InetSocketAddress("news.baidu.com", 80));
		OutputStream output = socket.getOutputStream();
		InputStreamReader isr = new InputStreamReader(socket.getInputStream(),
				"GBK");
		BufferedReader br = new BufferedReader(isr);
		StringBuilder request = new StringBuilder();
		request.append("GET /index.php HTTP/1.1\r\n");
		request.append("Accept-Language: zh-cn\r\n");
		request.append("Host: news.baidu.com\r\n");
		request.append("\r\n");
		output.write(request.toString().getBytes());
		output.flush();

		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str + "\n");
		}
		System.out.println(sb.toString());
		br.close();
		isr.close();
		output.close();
	}
}
