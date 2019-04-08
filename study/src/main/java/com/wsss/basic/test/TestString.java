package com.wsss.basic.test;

import java.io.ByteArrayOutputStream;
import java.util.TreeMap;

public class TestString {
	public static void main(String[] args) {
		String s = new String();
		String s2 = "123";
		
		int i1 = s.hashCode();
		int i2 = s2.hashCode();
		int i3 = s2.hashCode();
		System.out.println(i1);
		System.out.println(i2);
		System.out.println(i3);
		
		char[] i = "123".toCharArray();
		System.out.println(i[0]);
		
		int i4 = '1';
		System.out.println(i4);
		
		byte b1 = 1;
		switch(b1) {
			case 1:
				break;
			default:
				break;
		}
			
		TreeMap map = null;	
		try(ByteArrayOutputStream fis = new ByteArrayOutputStream()) {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int c = 1;
		long j = 1;
		c += j;
		System.out.println(c);
		
		String str = "123=";
		System.out.println(">>>>>>>"+str.substring(5));
		
		Number num = 2.2;
	}
}
