package com.wsss.basic.thread.interrupt;

public class ShutDownThread implements Runnable {
	private volatile boolean shutDown;
	@Override
	public void run() {
		while(!shutDown) {
			try {
				Thread.sleep(100000);
			}catch(Exception e) {
				System.out.println("error");
			}
		}
		System.out.println("shutdown");
	}
	
	public void shutDown() {
		System.out.println("invoke");
		this.shutDown = true;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		ShutDownThread sdt = new ShutDownThread();
		Thread t = new Thread(sdt);
		t.start();
		Thread.sleep(3000);
		System.out.println("start");
		sdt.shutDown();
		t.interrupt();
		System.out.println("end");
		Thread.sleep(3000);
	}
}
