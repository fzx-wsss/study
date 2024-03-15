package com.wsss.spi.dubbo;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Filter;

import java.util.Iterator;


public class SearchTest {
	public static void main(String[] args) {
		ExtensionLoader<Filter> s = ExtensionLoader.getExtensionLoader(Filter.class);
		Iterator<String> searchs = s.getSupportedExtensions().iterator();
		while (searchs.hasNext()) {
			System.out.println("test");
			Filter search = s.getExtension(searchs.next());
			System.out.println(search.getClass().getSimpleName());
		}
	}
}