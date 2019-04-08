package com.wsss.basic.thread.uncaughtException;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {  
			          
			        @Override  
			        public void uncaughtException(Thread t, Throwable e) {  
			            System.out.println("uncaughtExceptionHandler catch a Exception---------");  
			            System.out.println(e.getMessage());  
			        }
			    });
				throw new RuntimeException("error");
			}
		});
	      
	   // t.start();  
	    Thread.sleep(100000);  
	}
}
