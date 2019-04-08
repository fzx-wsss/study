package com.wsss.basic.distributed.locks.test;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.wsss.basic.distributed.locks.Lock;
import com.wsss.basic.distributed.locks.UnitTimeLock;

public class TestUnitTimeLock {
	public static void main(String[] args) {
		TestUnitTimeLock test = new TestUnitTimeLock();

		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(test.new TestTask());
			t.start();
		}
	}

	private class TestTask implements Runnable {
		public void run() {
			Lock lock = new UnitTimeLock(2, 20,"test6");
			while (true) {
				if (lock.tryLock()) {
					try {
						System.out.println(Thread.currentThread().getId()
								+ ":执行任务");
						System.out.println("count:" + lock.getCountValue());

						/*
						 * TestTask2 task2 = new
						 * TestTask2(lock.getConcurrencyDecr()); Thread t = new
						 * Thread(task2); t.start();
						 */

						TimeUnit.SECONDS.sleep(2);
						System.out.println(Thread.currentThread().getId()
								+ ":结束任务");
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unLock();
					}
				} else {
					System.out.println(Thread.currentThread().getId()
							+ "未获取到锁，1秒后重试");
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
}
