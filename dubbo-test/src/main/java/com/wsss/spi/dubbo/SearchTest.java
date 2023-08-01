package com.wsss.spi.dubbo;

import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.Iterator;


public class SearchTest {
	public static void main(String[] args) {
		ExtensionLoader<Search> s = ExtensionLoader.getExtensionLoader(Search.class);
		Iterator<String> searchs = s.getSupportedExtensions().iterator();
		while (searchs.hasNext()) {
			System.out.println("test");
			Search search = s.getExtension(searchs.next());
			search.search("test");
		}
	}
}