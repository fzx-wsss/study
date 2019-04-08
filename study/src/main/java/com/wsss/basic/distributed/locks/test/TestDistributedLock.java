package com.wsss.basic.distributed.locks.test;

import java.util.concurrent.TimeUnit;

import com.wsss.basic.distributed.locks.DistributedLock;

public class TestDistributedLock {
	public static void main(String[] args) {
		TestDistributedLock test = new TestDistributedLock();
		
		for(int i=0;i<2;i++) {
			Thread t = new Thread(test.new TestTask());
			t.start();
		}
	}
	
	private class TestTask implements Runnable {
		public void run() {
			while(true) {
				if(DistributedLock.tryLock("test1")) {
					try {
						System.out.println(Thread.currentThread().getId() + ":执行任务");
						TimeUnit.SECONDS.sleep(10);
						System.out.println(Thread.currentThread().getId() + ":结束任务");
					} catch(Exception e) {
						e.printStackTrace();
					} finally {
						DistributedLock.unLock("test1");
					}
				}else {
					System.out.println(Thread.currentThread().getId() + "未获取到锁，3秒后重试");
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
}
