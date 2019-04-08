package com.wsss.basic.thread.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class PrintQueue {
	private final Semaphore semaphore;
	
	public PrintQueue() {
		semaphore = new Semaphore(1);
	}
	
	public void printJob() {
		try {
			semaphore.acquire();
			
			int time = (int)(Math.random() * 3);
			System.out.println("print job start");
			// 随机暂停1-3秒
			TimeUnit.SECONDS.sleep(time);
			
			System.out.println("print job end");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			semaphore.release();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintQueue queue = new PrintQueue();
		
		for(int i=0;i<5;i++) {
			Thread thread = new Thread(new Job(queue));
			thread.start();
		}
	}
	
	static class Job implements Runnable {
		PrintQueue queue;
		
		public Job(PrintQueue queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//System.out.println("print ready");
			queue.printJob();
			//System.out.println("print end");
		}
		
	}
}
