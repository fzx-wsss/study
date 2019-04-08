package com.wsss.basic.generic.clazz;

import java.util.LinkedList;
import java.util.List;

public class Book<T> {
	private List<T> list = new LinkedList<T>();
	void print() {
		System.out.println(list);
	}
	
	void put(T e) {
		list.add(e);
	}
}
