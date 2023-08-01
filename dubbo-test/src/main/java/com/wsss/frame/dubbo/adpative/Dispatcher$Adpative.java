package com.wsss.frame.dubbo.adpative;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.Dispatcher;

@Adaptive
public class Dispatcher$Adpative implements Dispatcher {
	public ChannelHandler dispatch(ChannelHandler arg0, URL arg1) {
		if (arg1 == null) throw new IllegalArgumentException("url == null");
		URL url = arg1;
		String extName = url.getParameter("dispatcher",
				url.getParameter("dispather", url.getParameter("channel.handler", "all")));
		if (extName == null) throw new IllegalStateException(
				"Fail to get extension(com.alibaba.dubbo.remoting.Dispatcher) name from url(" + url.toString()
						+ ") use keys([dispatcher, dispather, channel.handler])");
		Dispatcher extension = (Dispatcher) ExtensionLoader
				.getExtensionLoader(Dispatcher.class).getExtension(extName);
		return extension.dispatch(arg0, arg1);
	}
}