package com.wsss.basic.distributed.locks.test;

import java.util.concurrent.TimeUnit;

import com.wsss.basic.distributed.locks.ConcurrencyLock;
import com.wsss.basic.distributed.locks.ConcurrencyLock.ConcurrencyDecr;
import com.wsss.basic.distributed.locks.Lock;
import com.wsss.basic.distributed.locks.Lock.lazyDecr;

public class TestConcurrencyLock {
	public static void main(String[] args) {
		TestConcurrencyLock test = new TestConcurrencyLock();
		
		for(int i=0;i<5;i++) {
			Thread t = new Thread(test.new TestTask());
			t.start();
		}
	}
	
	private class TestTask implements Runnable {
		public void run() {
			ConcurrencyLock lock = new ConcurrencyLock(2, "test6");
			while(true) {
				if(lock.tryLock()) {
					try {
						System.out.println(Thread.currentThread().getId() + ":执行任务");
						System.out.println("count:"+ lock.getCountValue());
						
						TestTask2 task2 = new TestTask2(lock.getConcurrencyDecr());
						Thread t = new Thread(task2);
						t.start();
						
						TimeUnit.SECONDS.sleep(2);
						System.out.println(Thread.currentThread().getId() + ":结束任务");
					} catch(Exception e) {
						e.printStackTrace();
					} finally {
						lock.unLock();
					}
				}else {
					System.out.println(Thread.currentThread().getId() + "未获取到锁，1秒后重试");
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	private static class TestTask2 implements Runnable {
		private lazyDecr concurrencyDecr;
		
		public TestTask2(ConcurrencyDecr concurrencyDecr) {
			this.concurrencyDecr = concurrencyDecr;
		}
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(10);
				concurrencyDecr.decr();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
