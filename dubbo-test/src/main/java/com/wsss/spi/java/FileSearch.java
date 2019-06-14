package com.wsss.spi.java;

public class FileSearch implements Search {
	public void search(String keyword) {
		System.out.println("now use file system search. keyword:" + keyword);
	}
}