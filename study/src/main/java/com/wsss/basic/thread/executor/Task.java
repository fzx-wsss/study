package com.wsss.basic.thread.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor类的方法
 * @author Administrator
 *
 */
public class Task implements Runnable {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		for(int i=0;i<100;i++) {
			Task task = new Task();
			executor.execute(task);
		}
		
		for(int i=0;executor.getActiveCount() <= 0;i++) {
			System.out.println("--------------------------------------------");
			System.out.println("getActiveCount:"+executor.getActiveCount());//返回执行器中正在执行任务的线程数					
			System.out.println("getCompletedTaskCount:"+executor.getCompletedTaskCount());//返回执行器已经完成的任务数
			System.out.println("getCorePoolSize:"+executor.getCorePoolSize());
			System.out.println("getLargestPoolSize:"+executor.getLargestPoolSize());
			System.out.println("getMaximumPoolSize:"+executor.getMaximumPoolSize());
			System.out.println("getPoolSize:"+executor.getPoolSize());//返回执行器线程池中的实际想成数	
			System.out.println("getTaskCount:"+executor.getTaskCount());
			System.out.println("--------------------------------------------");
			TimeUnit.SECONDS.sleep(1);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<10;i++) {
			try {
				System.out.println(Thread.currentThread().getId() + ":" + i);
				TimeUnit.SECONDS.sleep((int)(Math.random()*2));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
