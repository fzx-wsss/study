package com.wsss.basic.thread.fork.join;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class Processor extends RecursiveTask<List<String>> {
	
	String path;
	String search;
	
	public Processor(String path, String search) {
		super();
		this.path = path;
		this.search = search;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		ForkJoinPool pool = new ForkJoinPool();
		
		Processor processpr = new Processor("D://WorkSpace", ".*\\.xml");
		pool.execute(processpr);
		do {
			System.out.println("process" + pool.getActiveThreadCount());
			TimeUnit.SECONDS.sleep(1);
		}while(!processpr.isDone());
		
		pool.shutdown();
		for(String s : processpr.get()) {
			System.out.println(s);
		}
		System.out.println(processpr.get().size());
	}

	@Override
	protected List<String> compute() {
		// TODO Auto-generated method stub
		List<String> result = new ArrayList<>();
		List<Processor> processors = new ArrayList<>();
		
		File file = new File(path);
		File[] childFile = file.listFiles();
		for(File f:childFile) {
			if(f.isDirectory()) {
				Processor pro = new Processor(f.getAbsolutePath(), search);
				//pro.fork();
				invokeAll(pro);
				//pro.invoke();
				processors.add(pro);
			}else {
				if(f.getName().matches(search)) {
					result.add(f.getAbsolutePath());
				}
			}
		}
		if(processors.size()>50) {
			System.out.println("processors:" + processors.size());
		}
		for(Processor p:processors) {
			result.addAll(p.join());
		}
		return result;
	}

}
