package com.wsss.spi.dubbo;

public class DatabaseSearch implements Search {
	public void search(String keyword) {
		System.out.println("now use database search. keyword:" + keyword);
	}
}