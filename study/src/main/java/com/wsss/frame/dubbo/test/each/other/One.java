package com.wsss.frame.dubbo.test.each.other;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wsss.frame.dubbo.service.DemoService;
import com.wsss.frame.dubbo.service.TestService;


public class One {
	public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                new String[] { "classpath*:eachOther/One.xml" });  
        context.start();  
        
        TimeUnit.SECONDS.sleep(10);
        System.out.println("onw start");
        
        DemoService demoService = (DemoService) context.getBean("demoService"); //  
        String hello = demoService.sayHello("tom"); //
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
