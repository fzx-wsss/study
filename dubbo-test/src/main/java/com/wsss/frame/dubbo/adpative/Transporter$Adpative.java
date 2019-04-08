package com.wsss.frame.dubbo.adpative;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.remoting.RemotingException;

@Adaptive
public class Transporter$Adpative implements com.alibaba.dubbo.remoting.Transporter {
	public com.alibaba.dubbo.remoting.Server bind(com.alibaba.dubbo.common.URL arg0,
			com.alibaba.dubbo.remoting.ChannelHandler arg1) throws RemotingException {
		if (arg0 == null) throw new IllegalArgumentException("url == null");
		com.alibaba.dubbo.common.URL url = arg0;
		String extName = url.getParameter("server", url.getParameter("transporter", "netty"));
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.remoting.Transporter) name from url(" + url.toString()
						+ ") use keys([server, transporter])");
		com.alibaba.dubbo.remoting.Transporter extension = (com.alibaba.dubbo.remoting.Transporter) ExtensionLoader
				.getExtensionLoader(com.alibaba.dubbo.remoting.Transporter.class).getExtension(extName);
		return extension.bind(arg0, arg1);
	}

	public com.alibaba.dubbo.remoting.Client connect(com.alibaba.dubbo.common.URL arg0,
			com.alibaba.dubbo.remoting.ChannelHandler arg1) throws RemotingException {
		if (arg0 == null) throw new IllegalArgumentException("url == null");
		com.alibaba.dubbo.common.URL url = arg0;
		String extName = url.getParameter("client", url.getParameter("transporter", "netty"));
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.remoting.Transporter) name from url(" + url.toString()
						+ ") use keys([client, transporter])");
		com.alibaba.dubbo.remoting.Transporter extension = (com.alibaba.dubbo.remoting.Transporter) ExtensionLoader
				.getExtensionLoader(com.alibaba.dubbo.remoting.Transporter.class).getExtension(extName);
		return extension.connect(arg0, arg1);
	}
}