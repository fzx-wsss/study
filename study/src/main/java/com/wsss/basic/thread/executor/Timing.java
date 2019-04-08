package com.wsss.basic.thread.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 延时执行，循环执行，取消线程
 * @author Administrator
 *
 */
public class Timing {
	
	public static void main(String[] args) {
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(3);
		
		// 延时执行
		/*for(int i=0;i<5;i++) {
			Task task = new Task();
			executor.schedule(task, i+1, TimeUnit.SECONDS);
		}*/
		
		// 循环执行
		Task task = new Task();
		// 第三个参数，两次任务开始的时间间隔
		//ScheduledFuture<?> furure = executor.scheduleAtFixedRate(task, 5, 2, TimeUnit.SECONDS);
		// 第三个参数， 上一次任务结束到下一次任务开始的时间间隔
		ScheduledFuture<?> furure = executor.scheduleWithFixedDelay(task, 5, 2, TimeUnit.SECONDS);
		try {
			TimeUnit.SECONDS.sleep(furure.getDelay(TimeUnit.SECONDS) + 2);
			furure.cancel(false);
		
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown();
	}
	
	static class Task implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("start");
		}
		
	}
}
