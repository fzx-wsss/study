package com.wsss.basic.thread.collection;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * LinkedList
 * ConcurrentLinkedDeque 
 * ConcurrentLinkedQueue 一个基于链接节点的无界线程安全队列
 * LinkedBlockingDeque 一个基于已链接节点的、任选范围的阻塞双端队列。 
 * ArrayBlockingQueue：基于数组实现的一个阻塞队列，在创建ArrayBlockingQueue对象时必须制定容量大小。并且可以指定公平性与非公平性，默认情况下为非公平的，即不保证等待时间最长的队列最优先能够访问队列。
　　LinkedBlockingQueue：基于链表实现的一个阻塞队列，在创建LinkedBlockingQueue对象时如果不指定容量大小，则默认大小为Integer.MAX_VALUE。
　　PriorityBlockingQueue：以上2种队列都是先进先出队列，而PriorityBlockingQueue却不是，它会按照元素的优先级对元素进行排序，按照优先级顺序出队，每次出队的元素都是优先级最高的元素。注意，此阻塞队列为无界阻塞队列，即容量没有上限（通过源码就可以知道，它没有容器满的信号标志），前面2种都是有界队列。
　　DelayQueue：基于PriorityQueue，一种延时阻塞队列，DelayQueue中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素。DelayQueue也是一个无界队列，因此往队列中插入数据的操作（生产者）永远不会被阻塞，而只有获取数据的操作（消费者）才会被阻塞。
 * @author wsss
 *
 */
public class TestSyncQueque {
	public static void main(String[] args) throws InterruptedException {
		//Deque<String> list = new LinkedList<>();
		//Deque<String> list = new ConcurrentLinkedDeque<>();
		LinkedBlockingDeque<String> list = new LinkedBlockingDeque<>();
		
		
		Thread[] threads = new Thread[100];
		for(int i=0;i<threads.length;i++) {
			AddTask task = new AddTask(list);
			threads[i] = new Thread(task);
			threads[i].start();
		}
		
		System.out.println("AddTask threads start");
		for(int i=0;i<threads.length;i++) {
			threads[i].join();
		}
		
		// list.size()返回结果不一定正确
		System.out.println("list size:" + list.size());
		for(int i=0;i<threads.length;i++) {
			PollTask task = new PollTask(list);
			threads[i] = new Thread(task);
			threads[i].start();
		}
		System.out.println("PollTask threads start");
		for(int i=0;i<threads.length;i++) {
			threads[i].join();
		}
		System.out.println("list size:" + list.size());
	}
}

class AddTask implements Runnable {
	
	private Deque<String> deque;
	
	public AddTask(Deque<String> deque) {
		super();
		this.deque = deque;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String name = Thread.currentThread().getName();
		for(int i=0;i<10000;i++) {
			deque.add(name + ":" + i);
		}
	}
}

class PollTask implements Runnable {
	
	private Deque<String> deque;
	
	public PollTask(Deque<String> deque) {
		super();
		this.deque = deque;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i=0;i<5000;i++) {
			deque.pollFirst();
			deque.pollLast();
		}
	}
}