package com.wsss.basic.enumTest;

import java.util.concurrent.TimeUnit;

public enum enumMethod {
	
	SECOND(1,2) {
		public void test() {
			System.out.println("test");
		}

		@Override
		public void test2() {
			System.out.println("second");
		}
		
	},
	
	DAY(3,4) {
		public void test() {
			
		}
	};
	
	enumMethod(int i,int j) {
		
	}
	
	
	public abstract void test();
	
	public void test2() {
		System.out.println("test2");
	}
	static class test {
		public static void main(String[] args) {
			enumMethod.SECOND.test2();
			enumMethod.DAY.test2();
		}
	}
}
