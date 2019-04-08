package com.wsss.basic.thread.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * FutureTask类会在线程结束后调用done()方法，继承FutureTask类重写done()方法
 * @author Administrator
 *
 */
public class MyFutureTask extends FutureTask<String> {

	public MyFutureTask(Callable<String> callable) {
		super(callable);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void done() {
		// TODO Auto-generated method stub
		if(isCancelled()) {
			System.out.println("cancel done");
		}else {
			System.out.println("done");
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executure = Executors.newCachedThreadPool();
		MyFutureTask[] res = new MyFutureTask[5];
		for(int i=0;i<5;i++) {
			TimeUnit.SECONDS.sleep(1);
			res[i] = new MyFutureTask(new Task());
			
			executure.submit(res[i]);
		}
		
		TimeUnit.SECONDS.sleep(1);
		
		for(int i=0;i<5;i++) {
			res[i].cancel(true);
		}
		
		for(int i=0;i<5;i++) {
			if(!res[i].isCancelled()) {
				System.out.println(res[i].get());
			}else {
				//res[i].get();
				System.out.println("cancel");
			}
		}
		
		executure.shutdown();
	}
	
	static class Task implements Callable<String> {

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			TimeUnit.SECONDS.sleep((long)(Math.random()*7));
			return "hello world";
		}
		
	}

}
