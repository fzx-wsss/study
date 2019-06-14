package com.wsss.spi.java;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SearchTest {
	public static void main(String[] args) {
		ServiceLoader<Search> s = ServiceLoader.load(Search.class);
		Iterator<Search> searchs = s.iterator();
		while (searchs.hasNext()) {
			System.out.println("test");
			Search search = searchs.next();
			search.search("test");
		}
	}
}