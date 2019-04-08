package com.wsss.frame.spring.springboot.converter;

public class Person {
	private String name;
	private MyEnum myEnum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MyEnum getMyEnum() {
		return myEnum;
	}
	public void setMyEnum(MyEnum myEnum) {
		this.myEnum = myEnum;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", myEnum=" + myEnum + "]";
	}
	
}
