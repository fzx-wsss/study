package com.wsss.spi.java;

public class DatabaseSearch implements Search {
	public void search(String keyword) {
		System.out.println("now use database search. keyword:" + keyword);
	}
}