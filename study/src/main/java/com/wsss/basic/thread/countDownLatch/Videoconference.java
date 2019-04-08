package com.wsss.basic.thread.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Videoconference implements Runnable {
	CountDownLatch count;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		CountDownLatch count = new CountDownLatch(5);
		
		for(int i=0;i<5;i++) {
			Thread thread = new Thread(new Videoconference(count));
			thread.start();
		}
		
		count.await();
		System.out.println("all arrive");
	}
	
	public Videoconference(CountDownLatch count) {
		this.count = count;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long time = (long)(Math.random() * 5);
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("arrive");
		this.count.countDown();
		
	}
	
	
}
