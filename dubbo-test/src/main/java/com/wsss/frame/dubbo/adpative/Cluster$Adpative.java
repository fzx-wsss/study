package com.wsss.frame.dubbo.adpative;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.Directory;

@Adaptive
public class Cluster$Adpative implements Cluster {
	public Invoker join(Directory arg0) {
		if (arg0 == null)
			throw new IllegalArgumentException("com.alibaba.dubbo.rpc.cluster.Directory argument == null");
		if (arg0.getUrl() == null)
			throw new IllegalArgumentException("com.alibaba.dubbo.rpc.cluster.Directory argument getUrl() == null");
		URL url = arg0.getUrl();
		String extName = url.getParameter("cluster", "failover");
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.rpc.cluster.Cluster) name from url(" + url.toString()
						+ ") use keys([cluster])");
		Cluster extension = (Cluster) ExtensionLoader
				.getExtensionLoader(Cluster.class).getExtension(extName);
		return extension.join(arg0);
	}
}