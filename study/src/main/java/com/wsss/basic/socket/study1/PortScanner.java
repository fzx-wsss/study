package com.wsss.basic.socket.study1;

import java.io.IOException;
import java.net.Socket;

/**
 * 端口扫描器
 * @author Administrator
 *
 */
public class PortScanner {
	public static void main(String[] args) {
		PortScanner scan = new PortScanner();
		scan.scan("127.0.0.1");
	}
	
	public void scan(String address) {
		Socket socket = null;
		//扫描1到1024的端口
		for(int port=1;port<1024;port++) {
			try {
				socket = new Socket(address, port);
				System.out.println("成功建立连接：" + port);
			}catch(Exception e) {
				System.out.println("无法建立连接：" + port);
			}finally {
				if(null != socket) {
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
