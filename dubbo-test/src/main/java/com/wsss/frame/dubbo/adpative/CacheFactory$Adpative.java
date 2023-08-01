package com.wsss.frame.dubbo.adpative;


import org.apache.dubbo.cache.Cache;
import org.apache.dubbo.cache.CacheFactory;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Invocation;

@Adaptive
public class CacheFactory$Adpative implements CacheFactory {
	public Cache getCache(URL url, Invocation invocation) {
		String extName = url.getParameter("cache", "lru");
		if (extName == null)
			throw new IllegalStateException("Fail to get extension(com.alibaba.dubbo.cache.CacheFactory) name from url("
					+ url.toString() + ") use keys([cache])");
		CacheFactory extension = (CacheFactory) ExtensionLoader
				.getExtensionLoader(CacheFactory.class).getExtension(extName);
		return extension.getCache(url,invocation);
	}

}