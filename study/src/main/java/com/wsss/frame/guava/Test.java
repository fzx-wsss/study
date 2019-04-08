package com.wsss.frame.guava;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;

public class Test {
	public static void main(String[] args) {
		Long start = System.currentTimeMillis();  
        RateLimiter limiter = RateLimiter.create(10,1,TimeUnit.MINUTES); // 每秒不超过10个任务被提交  
        for (int i = 0; i < 10000; i++) {  
            limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞  
            System.out.println("call execute.." + i);  
              
        }  
        Long end = System.currentTimeMillis();  
          
        System.out.println(end - start);  
	}
}
