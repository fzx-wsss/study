package com.wsss.frame.spring.processor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.wsss.frame.spring.startup.TestStartUp;

public class Main {
	 public static void main(String[] args) {  
		 AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			context.register(Main.class);
			context.refresh();
			//Thread.sleep(10000);
			 
	        MyJavaBean bean = (MyJavaBean) context.getBean("myJavaBean");  
	        System.out.println("===============下面输出结果============");  
	        System.out.println("描述：" + bean.getDesc());  
	        System.out.println("备注：" + bean.getRemark());  
	        context.close(); 
	    } 
	 
	 @Bean
	 public MyJavaBean myJavaBean() {
		 System.out.println("myjavabean");
		 MyJavaBean bean = new MyJavaBean();
		 bean.setDesc("原始描述");
		 bean.setRemark("原始备注");
		 return bean;
	 }
	 
	 
	 @Bean
	 public MyPostProcessor myPostProcessor() {
		 return new MyPostProcessor();
	 }
	 
	 @Bean
	 public MyInstantiationAwareBeanPostProcessor myInstantiationAwareBeanPostProcessor() {
		 return new MyInstantiationAwareBeanPostProcessor();
	 }
}
