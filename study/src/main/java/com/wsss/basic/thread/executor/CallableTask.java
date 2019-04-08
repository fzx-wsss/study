package com.wsss.basic.thread.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * callable接口及返回结果
 * @author Administrator
 *
 */
public class CallableTask implements Callable<Integer> {
	
	private int num;
	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		Thread.currentThread().setName(num +"");
		
		int res = 1;
		for(int i=num;i>1;i--) {
			res *= i;
		}
		System.out.println("res:" + res);
		return res;
	}
	public CallableTask(int i) {
		this.num = i;
		
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		List<Future<Integer>> list = new ArrayList<>();
		for(int i=0;i<100;i++) {
			Random r = new Random();
			
			CallableTask task = new CallableTask(r.nextInt(10));
			Future<Integer> res = executor.submit(task);
			//System.out.println(res.get());
			list.add(res);
		}
		
		for(int i=0;i<list.size();i++) {
			System.out.println(list.get(i).get());
		}
		executor.shutdown();
	}
}
