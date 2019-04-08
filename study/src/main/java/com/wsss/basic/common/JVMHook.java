package com.wsss.basic.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JVMHook {
	public static void start() {
		final Map map = new HashMap<>();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
				TimeUnit.SECONDS.sleep(20);
				System.out.println("start:" + System.currentTimeMillis());
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		t.setDaemon(true);
		t.start();
		
		System.out.println("The JVM is started");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println("end"+System.currentTimeMillis());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println("end2"+System.currentTimeMillis());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		start();

		System.out.println("The Application is doing something");
	}
}
