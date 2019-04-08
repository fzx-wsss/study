package com.wsss.basic.generic.clazz;

public class BigBox implements Box<String> {

	@Override
	public Book<String> get() {
		// TODO Auto-generated method stub
		Book book = new Book<String>();
		book.put("123");
		return book;
	}

	@Override
	public void set(Book<String> t) {
		// TODO Auto-generated method stub
		
	}

}
