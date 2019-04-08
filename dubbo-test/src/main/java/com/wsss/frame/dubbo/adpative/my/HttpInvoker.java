package com.wsss.frame.dubbo.adpative.my;

import java.util.Map;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.protocol.AbstractInvoker;

public class HttpInvoker<T> extends AbstractInvoker<T> {

	public HttpInvoker(Class<T> type, URL url) {
		super(type, url);
	}

	@Override
	protected Result doInvoke(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		return new Result() {
			
			@Override
			public Object recreate() throws Throwable {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean hasException() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValue() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getResult() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Throwable getException() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String> getAttachments() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAttachment(String key, String defaultValue) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAttachment(String key) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}


}
