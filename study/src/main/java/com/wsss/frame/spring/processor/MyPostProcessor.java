package com.wsss.frame.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyPostProcessor implements BeanFactoryPostProcessor, BeanPostProcessor {
	/**
	 * 所有beanDefinition读取完成之后执行，只执行一次
	 */
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("调用MyBeanFactoryPostProcessor的postProcessBeanFactory");
		BeanDefinition bd = beanFactory.getBeanDefinition("myJavaBean");
		MutablePropertyValues pv = bd.getPropertyValues();
		if (pv.contains("remark")) {
			pv.addPropertyValue("remark", "在BeanFactoryPostProcessor中修改之后的备忘信息");
		}
	}
	/**
	 * bean创建完成之后，执行afterPropertiesSet或init-method方法之前，每个bean都会执行一次
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("BeanPostProcessor，对象" + beanName + "调用初始化方法之前的数据： " + bean.toString());
		return bean;
	}
	
	/**
	 * bean创建完成之后，执行afterPropertiesSet或init-method方法之后，每个bean都会执行一次
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("BeanPostProcessor，对象" + beanName + "调用初始化方法之后的数据：" + bean.toString());
		return bean;
	}

}