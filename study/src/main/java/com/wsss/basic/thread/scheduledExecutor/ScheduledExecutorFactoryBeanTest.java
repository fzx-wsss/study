package com.wsss.basic.thread.scheduledExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorFactoryBeanTest {
	
	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService executor =
				createExecutor(1, Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
				TimeUnit.SECONDS.sleep(3);
				System.out.println("123");
				} catch(Exception e) {
					
				}
			}
		}, 5, TimeUnit.SECONDS);
		
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
				TimeUnit.SECONDS.sleep(3);
				System.out.println("234");
				} catch(Exception e) {
					
				}
			}
		}, 5, TimeUnit.SECONDS);
		System.out.println(345);
		TimeUnit.SECONDS.sleep(100);
	}
	
	protected static ScheduledExecutorService createExecutor(
			int poolSize, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {

		return new ScheduledThreadPoolExecutor(poolSize, threadFactory, rejectedExecutionHandler);
	}
}
