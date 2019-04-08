package com.wsss.basic.thread.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor.invokeAny()方法，任意线程执行完后返回结果
 * @author Administrator
 *
 */
public class UserValidator {
	private String name;
	
	public UserValidator(String name) {
		this.name = name;
	}
	
	public boolean validator(String name,String pwd) {
		Random r = new Random();
		int time = r.nextInt(3);
		try {
			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r.nextBoolean();
	}
	
	
	public String getName() {
		return name;
	}
	
	public static void main(String[] args) {
		UserValidator user1 = new UserValidator("tom");
		UserValidator user2 = new UserValidator("puzz");
		
		TaskValidator task1 = new TaskValidator(user1, "tom", "tom");
		TaskValidator task2 = new TaskValidator(user2, "tom", "tom");
		
		List<TaskValidator> list = new ArrayList<>();
		list.add(task2);
		list.add(task1);
		
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		try {
			//invokeAny()方法
			String name = executor.invokeAny(list);
			System.out.println(name);
			
			//invokAll() 方法
			/*List<Future<String>> res = executor.invokeAll(list);
			System.out.println("invokAll");
			for(Future<String> f : res) {
				try {
					System.out.println(f.get());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown();
	}

	static class TaskValidator implements Callable<String> {
		private UserValidator validator;
		private String name;
		private String pwd;
		
		public TaskValidator(UserValidator validator, String name, String pwd) {
			this.validator = validator;
			this.name = name;
			this.pwd = pwd;
		}

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			System.out.println("call");
			if(!validator.validator(name, pwd)) {
				System.out.println("throw exception");
				throw new RuntimeException();
			}
			
			return validator.getName();
		}
		
	}
}
