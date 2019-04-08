package com.wsss.frame.spring.exception;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class SpringExcrption {
	//LifecycleProcessor not initialized
	/*public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
		applicationContext.setConfigLocation("application-context.xml");
		applicationContext.start();
		applicationContext.close();
	}*/
	
	//BeanFactory not initialized or already closed
	/*public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
		applicationContext.setConfigLocation("application-context.xml");
		applicationContext.getBean("xtayfjpk");
		applicationContext.close();
	}*/
	
	//ApplicationEventMulticaster not initialized
	/*public static void main(String[] args) {
		GenericApplicationContext parent = new GenericApplicationContext();
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.setParent(parent);
		context.refresh();
		context.start();
		context.close();
	}*/
}
