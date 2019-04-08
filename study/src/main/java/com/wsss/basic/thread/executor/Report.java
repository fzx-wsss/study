package com.wsss.basic.thread.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Report{
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		
		CompletionService<String> service = new ExecutorCompletionService<>(executorService);
		Request r1 = new Request("tom", service);
		Request r2 = new Request("puzz", service);
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		Processor processor = new Processor(service);
		Thread t3 = new Thread(processor);
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		executorService.shutdown();
		processor.setEnd(true);
		System.out.println("main end");
	}
	
	
	static class Generator implements Callable<String> {
		private String sender;
		private String title;
		
		public Generator(String sender, String title) {
			super();
			this.sender = sender;
			this.title = title;
		}

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			TimeUnit.SECONDS.sleep((long)(Math.random()*10));
			System.out.println(sender + ":" + title);
			return sender + ":" + title;
		}
	}
	
	static class Request implements Runnable {
		private String name;
		private CompletionService<String> service;
		
		public Request(String name, CompletionService<String> service) {
			this.name = name;
			this.service = service;
		}


		@Override
		public void run() {
			// TODO Auto-generated method stub
			Generator generator = new Generator(name, "tom");
			System.out.println("submit");
			service.submit(generator);
		}
	}
	
	static class Processor implements Runnable {
		private CompletionService<String> service;
		private boolean end;
		
		public Processor(CompletionService<String> service) {
			this.service = service;
		}


		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!end) {
				Future<String> future = null;
				try {
					future = service.poll(20,TimeUnit.SECONDS);
					//future = service.take();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(future);
				if(null != future) {
					try {
						String res = future.get();
						System.out.println(res);
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			System.out.println("pro end");
		}


		public void setEnd(boolean end) {
			this.end = end;
		}
		
		
	}
}
