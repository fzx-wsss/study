package com.wsss.frame.dubbo.adpative;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.remoting.*;

@Adaptive
public class Transporter$Adpative implements Transporter {
	public RemotingServer bind(URL arg0,
							   ChannelHandler arg1) throws RemotingException {
		if (arg0 == null) throw new IllegalArgumentException("url == null");
		URL url = arg0;
		String extName = url.getParameter("server", url.getParameter("transporter", "netty"));
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.remoting.Transporter) name from url(" + url.toString()
						+ ") use keys([server, transporter])");
		Transporter extension = (Transporter) ExtensionLoader
				.getExtensionLoader(Transporter.class).getExtension(extName);
		return extension.bind(arg0, arg1);
	}


	public Client connect(URL arg0,
			ChannelHandler arg1) throws RemotingException {
		if (arg0 == null) throw new IllegalArgumentException("url == null");
		URL url = arg0;
		String extName = url.getParameter("client", url.getParameter("transporter", "netty"));
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.remoting.Transporter) name from url(" + url.toString()
						+ ") use keys([client, transporter])");
		Transporter extension = (Transporter) ExtensionLoader
				.getExtensionLoader(Transporter.class).getExtension(extName);
		return extension.connect(arg0, arg1);
	}
}