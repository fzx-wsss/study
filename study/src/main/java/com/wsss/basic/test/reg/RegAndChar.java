package com.wsss.basic.test.reg;

import java.util.Arrays;
import java.util.regex.Pattern;

public class RegAndChar {
	static long startTime;
	static long endTime;
	
	static {
		startTime = System.currentTimeMillis();
	}
	
	public static void main(String[] args) {
		for(int i=0;i<1000000;i++) {
			//此程序使用内存为:12963kb
			//此程序使用时间为：3186毫秒
			/*useReg("AB CDE FG", "AB");
			useReg("AB CDE FG", "CDE");
			useReg("AB CDE FG", "CD");*/
			
			//此程序使用内存为:193561kb
			//此程序使用时间为：601毫秒
			/*useSplit("AB CDE FG", "AB");
			useSplit("AB CDE FG", "CDE");
			useSplit("AB CDE FG", "CD");*/
			
			//此程序使用内存为:665kb
			//此程序使用时间为：30毫秒
			/*useSpring("AB CDE FG", "AB");
			useSpring("AB CDE FG", "CDE");
			useSpring("AB CDE FG", "CD");*/
			
			//此程序使用内存为:57818kb
			//此程序使用时间为：110毫秒
			/*useChar("AB CDE FG", "AB");
			useChar("AB CDE FG", "CDE");
			useChar("AB CDE FG", "CD");*/
			
		}
		
		/*--------------------------------------------------------------*/
		test();
	}
	
	public static boolean useReg(String s1,String s2) {
		 return Pattern.compile("(.* )*" + s2 + "( .*)*").matcher(s1).matches();
	}
	
	public static boolean useSplit(String s1,String s2) {
		for(String s:s1.split(" ")) {
			if(s.equals(s2)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean useChar(String s1,String s2) {
		 char[] char1 = s1.toCharArray();
		 char[] char2 = s2.toCharArray();
		 for(int i=0;i<char1.length;i++) {
			 int j=0;
			 for(;j<char2.length;j++) {
				 if(char1[i+j] != char2[j]) {
					 break;
				 }
			 }
			 if(j == char2.length 
					 && (i+j >= char1.length || char1[i+j] == ' ')
					 && (i-1 <0 || char1[i-1] == ' ')) {
				 return true;
			 }
			 
		 }
		 return false;
	}
	
	public static boolean useSpring(String s1,String s2) {
		int length1 = s1.length();
	    int length2 = s2.length();
	    int limit = length1 - length2;
	    char last = ' ';
	    for (int i = 0; i <= limit; i++) {
	      if (last == ' ' // 左
	          && (i == limit || s1.charAt(i + length2) == ' ') // 右
	          && s1.regionMatches(i, s2, 0, length2)) {
	        return true;
	      }
	      last = s1.charAt(i);
	    }
	    return false;
	}
	
	public static void test() {
		endTime = System.currentTimeMillis();
		long usedTime = endTime - startTime;
		long usedMemory = (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024;
		System.out.println("此程序使用内存为:" + usedMemory + "kb");
		System.out.println("此程序使用时间为：" + usedTime + "毫秒");
	}
}
