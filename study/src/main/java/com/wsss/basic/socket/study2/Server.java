package com.wsss.basic.socket.study2;

import java.io.*;
import java.net.*;

/**
 * 设置请求队列长度
 * 绑定IP地址
 * 读取数据
 * 获取绑定的ip地址端口号
 * 不accept请求，队列满了之后会拒绝接下来的请求
 * @author Administrator
 *
 */
public class Server {
  private int port=8000;
  private ServerSocket serverSocket;

  public Server() throws IOException {
    //serverSocket = new ServerSocket(port,3);
	 //绑定了ip地址，通过其他ip访问不了
    serverSocket = new ServerSocket(port,3,InetAddress.getByName("192.168.14.58"));
    //连接请求队列的长度为3
    System.out.println(serverSocket.getInetAddress());
    System.out.println(serverSocket.getLocalSocketAddress());
    System.out.println(serverSocket.getLocalPort());
    System.out.println("服务器启动");
  }

  public void service() {
    while (true) {
      Socket socket=null;
      try {
        socket = serverSocket.accept();  //从连接请求队列中取出一个连接
        System.out.println("New connection accepted " +
        socket.getInetAddress() + ":" +socket.getPort());
        
        /*BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = null;
        while((s=br.readLine()) != null) {
        	System.out.println(s);
        }
        System.out.println(socket.getLocalAddress().getHostAddress());*/
      }catch (IOException e) {
         e.printStackTrace();
      }finally {
         try{
           if(socket!=null)socket.close();
         }catch (IOException e) {e.printStackTrace();}
      }
    }
  }

  public static void main(String args[])throws Exception {
    Server server=new Server();
    //Thread.sleep(60000*10);  //睡眠十分钟
    server.service();
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程精解>>                       *
 * 技术支持网址：www.javathinker.org                *
 ***************************************************/
