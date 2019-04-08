package com.wsss.basic.thread.threadLocal;

import java.util.concurrent.TimeUnit;

import com.wsss.basic.thread.Time;

public class TestThreadLocal {
	static ThreadLocal<String> tl = new ThreadLocal<>();
	
	public static void main(String[] args) {
		for(int i=0;i<3;i++) {
			final String num = String.valueOf(i);
			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					tl.set(num);
					try {
						TimeUnit.SECONDS.sleep(5);
					}catch(Exception e) {
						
					}
					System.out.println(tl.get());
				}
			});
			
			t1.start();
		}
		System.out.println("end");
	}
	
}
