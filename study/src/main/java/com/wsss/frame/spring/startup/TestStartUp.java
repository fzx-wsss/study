package com.wsss.frame.spring.startup;

import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class TestStartUp implements SmartLifecycle {
	private boolean start = false;
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(TestStartUp.class);
		context.refresh();
		Thread.sleep(10000);
		context.close();
	}
	@Bean
	public TestStartUp testStartUp() {
		return new TestStartUp();
	}
	@Override
	public void start() {
		System.out.println("start");
		start = true;
	}

	@Override
	public void stop() {
		System.out.println("stop");
		start = false;
	}

	@Override
	public boolean isRunning() {
		System.out.println("isRunning");
		return start;
	}

	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		// TODO Auto-generated method stub
		System.out.println(callback);
		System.out.println("stop callback");
	}

}
