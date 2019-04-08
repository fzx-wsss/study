package com.wsss.basic.thread.lock.readwriteLock.copy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PricesInfo {
	private ReadWriteLock lock;
	
	private double price1;
	private double price2;
	
	public static void main(String[] args) {
		PricesInfo info = new PricesInfo(1D, 2D);
		
		reader[] readers = new reader[5];
		Thread[] threads = new Thread[5];
		for(int i=0;i<5;i++) {
			readers[i] = new reader(info);
			threads[i] = new Thread(readers[i]);
		}
		
		writer write = new writer(info);
		Thread thread = new Thread(write);
		
		for(int i=0;i<5;i++) {
			threads[i].start();
		}
		thread.start();
	}
	
	public PricesInfo(double price1, double price2) {
		this.lock = new ReentrantReadWriteLock();
		this.price1 = price1;
		this.price2 = price2;
	}
	public double getPrice1() {
		lock.readLock().lock();
		try {
			double price = price1;
			System.out.println(Thread.currentThread().getName() + " price1 " + price);
		} finally {

			lock.readLock().unlock();
		}
		return price1;
	}
	public double getPrice2() {
		lock.readLock().lock();
		try {
			double price = price2;
			System.out.println(Thread.currentThread().getName() + " price2 " + price);
		} finally {
			lock.readLock().unlock();
		}
		return price2;
	}
	public void setPrices(double price1,double price2) {
		lock.writeLock().lock();
		try {
			System.out.println("write start");
			this.price1 = price1;
			this.price2 = price2;
			System.out.println("write end");
		}finally {
			lock.writeLock().unlock();
		}
	}
	
	static class reader implements Runnable {
		PricesInfo info;
		
		public reader(PricesInfo info) {
			this.info = info;
		}
		
		@Override
		public void run() {
			for(int i=0;i<10;i++) {
				info.getPrice1();
				info.getPrice2();
				
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	static class writer implements Runnable {
		PricesInfo info;
		
		public writer(PricesInfo info) {
			this.info = info;
		}
		
		@Override
		public void run() {
			for(int i=0;i<5;i++) {
				info.setPrices(Math.random()*10, Math.random()*5);
				
				try {
					TimeUnit.SECONDS.sleep(6);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
