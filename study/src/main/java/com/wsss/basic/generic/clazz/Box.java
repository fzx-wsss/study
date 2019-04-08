package com.wsss.basic.generic.clazz;

public interface Box<T> {
	Book<T> get();
	void set(Book<T> t);
}
