package com.wsss.basic.thread.threadPool;

public class TestMyThreadPool {
	public static void main(String[] args) throws InterruptedException {
		MyThreadPool pool = new MyThreadPool(5,3);
		//ThreadPool pool = new ThreadPool(3);
		for(int i=0;i<5;i++) {
			TestTask task = new TestTask();
			pool.execute(task);
		}
		//Thread.sleep(1000);
		pool.shutDown();
		
	}
	
	static class TestTask implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				System.out.println("开始任务");
				Thread.sleep(3000);
				System.out.println("结束任务");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("任务被打断");
			}
		}
	}
}
