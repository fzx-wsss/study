package com.wsss.frame.dubbo.adpative.my;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.RpcException;

public class HttpProtocol implements Protocol {

	@Override
	public int getDefaultPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
		// TODO Auto-generated method stub
		return new HttpExporter<T>(invoker);
	}

	@Override
	public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
		// TODO Auto-generated method stub
		return new HttpInvoker<>(type, url);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		return;
	}

}
