package com.wsss.basic.socket.study1;
import java.io.*;
import java.net.*;
public class SimpleClient {
  public static void main(String args[])throws Exception {
    Socket s1 = new Socket("localhost",9999);
    System.out.println("第一次连接成功");
    Socket s2 = new Socket("localhost",9999);
    System.out.println("第二次连接成功");
    Socket s3 = new Socket("localhost",9999);
    System.out.println("第三次连接成功");
    s1.close();s2.close();s3.close();
  }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<Java网络编程精解>>                       *
 * 技术支持网址：www.javathinker.org                *
 ***************************************************/
