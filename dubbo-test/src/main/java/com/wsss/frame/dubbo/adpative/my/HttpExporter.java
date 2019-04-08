package com.wsss.frame.dubbo.adpative.my;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.protocol.AbstractExporter;

public class HttpExporter<T> extends AbstractExporter<T>{

	public HttpExporter(Invoker<T> invoker) {
		super(invoker);
	}


}
