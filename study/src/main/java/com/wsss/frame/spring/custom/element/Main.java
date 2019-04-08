package com.wsss.frame.spring.custom.element;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wsss.frame.spring.helloworld.HelloWorld;

public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"com/wsss/frame/spring/custom/element/spring.xml");

		User obj = (User) context.getBean("test");
		System.out.println(obj);
	}
}
