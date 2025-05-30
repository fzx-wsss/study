package com.wsss.frame.dubbo.model;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = -851256193281742726L;
	
	private String name;
	private int age;
	private String sex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				", sex='" + sex + '\'' +
				'}';
	}
}
