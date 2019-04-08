package com.wsss.basic.generic.clazz;

public class Test {
	public static void main(String[] args) {
		Box<?> box = new BigBox();
		Book<?> book = box.get();
		book.print();
	}
}
