package com.wsss.basic.thread.interrupt;

public class TestInterrupt {
	public static void main(String[] args) {
		Task task = new Task();
		task.start();
		task.interrupt();
		System.out.println("中断");
	}
	
	static class Task extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			System.out.println("开始睡眠");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("被中断");
			}
			
			System.out.println(Thread.currentThread().isInterrupted());
		}
		
	}
}	
