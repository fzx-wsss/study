package com.wsss.frame.dubbo.adpative.my;

import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.protocol.AbstractExporter;

public class HttpExporter<T> extends AbstractExporter<T> {

	public HttpExporter(Invoker<T> invoker) {
		super(invoker);
	}


}
