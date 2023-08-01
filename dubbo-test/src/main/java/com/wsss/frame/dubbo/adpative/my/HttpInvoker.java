package com.wsss.frame.dubbo.adpative.my;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.protocol.AbstractInvoker;

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
			public void setValue(Object value) {

			}


			@Override
			public Throwable getException() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setException(Throwable t) {

			}

			@Override
			public Map<String, String> getAttachments() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> getObjectAttachments() {
				return null;
			}

			@Override
			public void addAttachments(Map<String, String> map) {

			}

			@Override
			public void addObjectAttachments(Map<String, Object> map) {

			}

			@Override
			public void setAttachments(Map<String, String> map) {

			}

			@Override
			public void setObjectAttachments(Map<String, Object> map) {

			}

			@Override
			public String getAttachment(String key, String defaultValue) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getObjectAttachment(String key, Object defaultValue) {
				return null;
			}

			@Override
			public void setAttachment(String key, String value) {

			}

			@Override
			public void setAttachment(String key, Object value) {

			}

			@Override
			public void setObjectAttachment(String key, Object value) {

			}

			@Override
			public Result whenCompleteWithContext(BiConsumer<Result, Throwable> fn) {
				return null;
			}

			@Override
			public <U> CompletableFuture<U> thenApply(Function<Result, ? extends U> fn) {
				return null;
			}

			@Override
			public Result get() throws InterruptedException, ExecutionException {
				return null;
			}

			@Override
			public Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
				return null;
			}

			@Override
			public String getAttachment(String key) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getObjectAttachment(String key) {
				return null;
			}
		};
	}


}
