package com.wsss.frame.dubbo.test.each.other;

import java.util.concurrent.TimeUnit;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wsss.frame.dubbo.service.TestService;

public class Two {
	public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath*:eachOther/Two.xml"});  
        context.start();
        
        TestService testService = (TestService) context.getBean("testService"); //
        TimeUnit.SECONDS.sleep(10);
        System.out.println("two start");
        
        
        testService.test();
        
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟  
    }  
}
