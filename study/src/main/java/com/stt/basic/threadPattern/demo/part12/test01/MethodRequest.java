package com.stt.basic.threadPattern.demo.part12.test01;

public abstract class MethodRequest {
	protected final Servant servant;
	protected  final FutureResult future;
	protected MethodRequest(Servant servant,FutureResult future){
		this.servant = servant;
		this.future = future;
	}
	
	public abstract void execute();
	
}

