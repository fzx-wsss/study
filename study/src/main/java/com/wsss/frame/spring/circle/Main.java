package com.wsss.frame.spring.circle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.wsss.frame.spring.startup.TestStartUp;

public class Main {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.setAllowCircularReferences(false);
		context.scan("com.wsss.frame.spring.circle");;
		context.refresh();
		
		TestA ta = context.getBean("testA",TestA.class);
		System.out.println(ta);
	}
}
