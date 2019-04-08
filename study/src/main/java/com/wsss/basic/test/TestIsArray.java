package com.wsss.basic.test;

public class TestIsArray {
	public static void main(String[] args) {
		byte[] b = "hello world".getBytes();
		Object o = b;
		System.out.println(o instanceof byte[]);
		System.out.println(o instanceof Byte[]);
		System.out.println(o.getClass().isArray());
		
		byte[][] b2 = new byte[2][];
		b2[0] = b;
		b2[1] = b;
		Object o2 = b2;
		System.out.println(o2 instanceof byte[][]);
		System.out.println(o2 instanceof byte[]);
		System.out.println(o2.getClass().isArray());
	}
}
