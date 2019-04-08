package com.wsss.frame.dubbo.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/com/wsss/frame/dubbo/test/Provider.xml");  
        context.start(); 
        context.getBean("demoService");
        System.out.println("start");
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟  
    }  
}
