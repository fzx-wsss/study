package com.wsss.frame.dubbo.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wsss.frame.dubbo.service.DemoService;
import com.wsss.frame.dubbo.service.TestService;


public class Consumer {
	public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                new String[] { "classpath*:consumer.xml" });  
        context.start();  
        
        TestService testService = (TestService) context.getBean("testService"); //
        TestService testService2 = (TestService) context.getBean("testService");
        testService.test();
        
        DemoService demoService = (DemoService) context.getBean("demoService"); //  
        String hello = demoService.sayHello("tom"); // ִ  
        System.out.println(hello); //   
  
        //   
        List list = demoService.getUsers();  
        if (list != null && list.size() > 0) {  
            for (int i = 0; i < list.size(); i++) {  
                System.out.println(list.get(i));  
            }  
        }  
        // System.out.println(demoService.hehe());  
        System.in.read();  
    }  
}
