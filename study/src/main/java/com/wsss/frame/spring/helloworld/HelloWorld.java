package com.wsss.frame.spring.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorld {
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void printMessage() {
		System.out.println("Your Message : " + message);
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"com/wsss/frame/spring/helloworld/*.xml");

			HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
			Bean bean = (Bean) context.getBean("bean");
			obj.printMessage();
		} catch (Exception e) {
			System.out.println(1);
		}

	}
}
