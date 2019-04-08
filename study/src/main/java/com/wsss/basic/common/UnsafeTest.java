package com.wsss.basic.common;


import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import sun.misc.Unsafe;
public class UnsafeTest {
	private static final int _1MB = 1024*1024;
	
	public static void main(String[] args) throws Exception {
		LinkedList<Byte[]> list = new LinkedList<>();
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe) unsafeField.get(null);
		for(int i=0;i<20000;i++) {
			System.out.println(i);
			long allocatedAddress = unsafe.allocateMemory(_1MB);
			unsafe.putLong(allocatedAddress, 1);
			//list.add(new Byte[_1MB]);
		}
		TimeUnit.SECONDS.sleep(1000);
	}
}
