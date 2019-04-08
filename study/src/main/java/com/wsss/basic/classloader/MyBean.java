package com.wsss.basic.classloader;

public class MyBean {
	private Mybean2 bean = new Mybean2();

	public Mybean2 getBean() {
		return bean;
	}

	public void setBean(Mybean2 bean) {
		this.bean = bean;
	}
}
