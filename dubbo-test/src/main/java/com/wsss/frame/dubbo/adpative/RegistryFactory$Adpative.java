package com.wsss.frame.dubbo.adpative;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;

@Adaptive
public class RegistryFactory$Adpative implements RegistryFactory {
	public Registry getRegistry(URL arg0) {
		if (arg0 == null) throw new IllegalArgumentException("url == null");
		URL url = arg0;
		String extName = (url.getProtocol() == null ? "dubbo" : url.getProtocol());
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.registry.RegistryFactory) name from url(" + url.toString()
						+ ") use keys([protocol])");
		RegistryFactory extension = (RegistryFactory) ExtensionLoader
				.getExtensionLoader(RegistryFactory.class).getExtension(extName);
		return extension.getRegistry(arg0);
	}
}