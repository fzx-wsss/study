package com.wsss.basic.thread.fork.join;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class Product {
	private String name;
	private double price;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static void main(String[] args) {
		List<Product> list = Generator.generator(100000);
		
		Task t = new Task(list, 0, list.size(), 0.2);
		t.updatePrices();
		
		System.out.println("one thread end");
		
		ForkJoinPool pool = new ForkJoinPool();
		//invoke是同步方法，execute是异步方法,submit跟execute一样，只是多了一个返回值
		pool.invoke(t);
		pool.execute(t);
		//pool.submit(t);
		System.out.println("wait");
		while(!t.isDone()) {
			System.out.println("thread count:" + pool.getActiveThreadCount());
			System.out.println("getStealCount:" + pool.getStealCount());
			System.out.println("getStealCount:" + pool.getParallelism());
			
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		pool.shutdown();
		
		if(t.isCompletedNormally()) {
			System.out.println("success");
		}
		
		/*for(int i=0;i<list.size();i++) {
			if(list.get(i).getPrice() != 2D) {
				System.out.println(list.get(i).getName() + ":" + list.get(i).getPrice());
			}
			
		}*/
	}
}

class Generator {
	public static List<Product> generator(int num) {
		List<Product> ret = new ArrayList<>();
		for(int i=0;i<num;i++) {
			Product p = new Product();
			p.setName("product"+i);
			p.setPrice(10);
			ret.add(p);
		}
		return ret;
	}
}

class Task extends RecursiveAction {
	private static final long serialVersionUID = 1l; 
	
	private List<Product> list;
	private int first;
	private int last;
	private double increment;
	
	public Task(List<Product> list, int first, int last, double increment) {
		super();
		this.list = list;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}

	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		if(last-first<10) {
			updatePrices();
		}else {
			int middle = (first+last)/2;
			//System.out.println("");
			Task t1 = new Task(list, first, middle+1, increment);
			Task t2 = new Task(list, middle+1, last, increment);
			invokeAll(t1, t2);
			//t1.fork();
			t2.fork();
			t1.invoke();
			t2.invoke();
		}
	}
	
	public void updatePrices() {
		for(int i=first;i<last;i++) {
			Product p = list.get(i);
			p.setPrice(p.getPrice()*increment);
			try {
				if(i%100 == 0) {
					Thread.sleep(2);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}