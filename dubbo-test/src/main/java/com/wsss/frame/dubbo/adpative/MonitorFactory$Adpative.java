package com.wsss.frame.dubbo.adpative;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.monitor.Monitor;
import org.apache.dubbo.monitor.MonitorFactory;

@Adaptive
public class MonitorFactory$Adpative implements MonitorFactory {
	public Monitor getMonitor(URL arg0) {
		if (arg0 == null) throw new IllegalArgumentException("url == null");
		URL url = arg0;
		String extName = (url.getProtocol() == null ? "dubbo" : url.getProtocol());
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.monitor.MonitorFactory) name from url(" + url.toString()
						+ ") use keys([protocol])");
		MonitorFactory extension = (MonitorFactory) ExtensionLoader
				.getExtensionLoader(MonitorFactory.class).getExtension(extName);
		return extension.getMonitor(arg0);
	}
}