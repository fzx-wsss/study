package com.wsss.basic.thread.threadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

public class MyThreadPool extends ThreadGroup implements Executor {
	
	private volatile boolean isClosed = false;
	private BlockingQueue<Runnable> waitingQueue = new LinkedBlockingDeque<>();
	private int maxSize;
	private WorkHander[] works;
	
	private MyThreadPool(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	private MyThreadPool() {
		this("myThreadPool");
	}
	
	public MyThreadPool(int maxSize,int workSize) {
		this();
		this.maxSize = maxSize;
		this.works = new WorkHander[workSize];
		for(int i=0;i<works.length;i++) {
			works[i] = new WorkHander();
			works[i].start();
		}
	}
	
	
	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		if(isClosed) {
			System.out.println("线程池已关闭");
			return;
		}
		if(waitingQueue.size() > maxSize) {
			System.out.println("超出队列长度" + command);
			return;
		}
		
		waitingQueue.add(command);
	}
	
	/**
	 * 立刻关闭，结束所有任务
	 */
	public void shutDownNow() {
		synchronized(this) {
			if(isClosed) {
				return;
			}
			isClosed = true;
		}
		waitingQueue.clear();
		//interrupt();
		
		for(WorkHander work : works) {
			try {
				//如果在WorkHander中可以关闭线程池，则此处可能造成死锁
				work.interrupt();
				work.join();
			} catch (InterruptedException e) {
				System.out.println("等待工作线程出错" + work);
			}
		}
		
		System.out.println("线程池结束");
	}
	
	/**
	 * 执行完所有任务后关闭
	 */
	public void shutDown() {
		synchronized(this) {
			if(isClosed) {
				return;
			}
			isClosed = true;
		}
			
		for(WorkHander work : works) {
			try {
				//System.out.println(work.isWaiting());
				//这里有问题，可能会中断任务，违背方法的本意
				if(work.isWaiting()) {
					//System.out.println(work.isWaiting());
					work.interrupt();
				}
				//如果在WorkHander中可以关闭线程池，则此处可能造成死锁
				work.join();
			} catch (InterruptedException e) {
				System.out.println("等待工作线程出错" + work);
			}
		}
		
		System.out.println("线程池结束");
	}
	
	class WorkHander extends Thread {
		private Runnable task;
		private volatile boolean workStatus;
		
		private Runnable getNext() {
			try {
				workStatus = true;
				return waitingQueue.take();
			}catch(InterruptedException e) {
				System.out.println("线程退出" + Thread.currentThread().getName());
				return null;
			}finally {
				workStatus = false;
			}
			
		}
		
		// 是否空闲
		public boolean isWaiting() {
			return workStatus;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!isClosed || waitingQueue.size() != 0) {
				task = getNext();
				if(null == task) {
					continue;
				}
				
				try {
					task.run();
				}catch(Exception e) {
					System.out.println("执行任务出错");
				}
			}
			System.out.println("线程池线程结束");
		}
		
	}

}
