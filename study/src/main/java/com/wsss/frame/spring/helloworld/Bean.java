package com.wsss.frame.spring.helloworld;

public class Bean {
	static {
		System.out.println("static");
	}
	
	{
		System.out.println("nonstatic");
	}
}
