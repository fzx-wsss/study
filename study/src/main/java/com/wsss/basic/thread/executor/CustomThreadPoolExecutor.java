package com.wsss.basic.thread.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class CustomThreadPoolExecutor {
	public static void main(String[] args) {
		ThreadPoolExecutor ex = new ThreadPoolExecutor(//
                1, //
                10, //
                1000 * 60, //
                TimeUnit.MILLISECONDS, //
                new LinkedBlockingQueue<Runnable>(), //
                new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						// TODO Auto-generated method stub
						return new Thread(r);
					}
                	
                });
		for(int i=0;i<1000;i++) {
			ex.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(60);
					}catch(Exception e) {
						
					}
				}
			});
		}
	}
}
