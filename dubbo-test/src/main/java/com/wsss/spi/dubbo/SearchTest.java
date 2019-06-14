package com.wsss.spi.dubbo;

import java.util.Iterator;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

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