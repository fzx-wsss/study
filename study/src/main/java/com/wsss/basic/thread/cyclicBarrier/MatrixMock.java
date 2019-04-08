package com.wsss.basic.thread.cyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MatrixMock {
	private int data[][];
	private int result[];
	
	public MatrixMock(int size,int length,int number) {
		data = new int[size][length];
		result = new int[size];
		int counter = 0;
		Random random = new Random();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < length; j++) {
				data[i][j] = random.nextInt(10);
				if (data[i][j] == number) {
					counter++;
				}
			}
		}

		System.out.println("总数：" + counter + ";数字：" + number);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatrixMock mock = new MatrixMock(5, 1000, 5);
		Group group = new Group(mock.result);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5,group);
		
		for(int i=0;i<5;i++) {
			Thread thread = new Thread(new Search(mock,5,i,cyclicBarrier));
			thread.start();
		}
	}
	
	static class Search implements Runnable {
		private MatrixMock mock;
		private int number;
		private int row;
		private final CyclicBarrier barrier;
		public Search(MatrixMock mock,int number,int row,CyclicBarrier barrier) {
			this.mock = mock;
			this.number = number;
			this.row = row;
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			int count = 0;
			for(int i=0;i<mock.data[row].length;i++) {
				if(mock.data[row][i] == number) {
					count++;
				}
			}
			
			mock.result[row] = count;
			System.out.println("search end");
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	static class Group implements Runnable {
		private int[] results;
		
		public Group(int[] results) {
			this.results = results;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("统计 start");
			int result = 0;
			for(int i=0;i<results.length;i++) {
				result += results[i];
			}
			
			System.out.println("统计：" + result);
		}
		
	}
}
