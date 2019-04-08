package com.wsss.basic.thread.interrupt;

import java.util.concurrent.TimeUnit;

public class PrimeGenerator extends Thread {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int number = 0;
		while(true) {
			if(isPrime(number)) {
				System.out.println(number);
			}
			
			if(isInterrupted()) {
				System.out.println("interrupted");
				return;
			}
			number++;
		}
	}
	
	private boolean isPrime(int number) {
		if(number < 2) {
			return true;
		}
		
		for(int i=2;i<10;i++) {
			if(number%i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread task = new PrimeGenerator();
		task.start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		}catch(Exception e) {
			System.out.println(2);
		}
		task.interrupt();
	}

}
