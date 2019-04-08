package com.wsss.frame.dubbo.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider2 {
	public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath*:Provider2.xml"});  
        context.start();  
   
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟  
    }  
}
