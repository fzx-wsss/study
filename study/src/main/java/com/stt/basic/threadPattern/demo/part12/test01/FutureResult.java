package com.stt.basic.threadPattern.demo.part12.test01;

public class FutureResult extends Result{

	private Result result = null;
	private boolean ready = false;
	
	public synchronized void setResult(Result result){
		if(ready){
			return;
		}
		this.ready = true;
		this.result = result;
		notifyAll();	
	}
	
	@Override
	public synchronized Object getResultValue() {
		while(!ready){
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		return result.getResultValue();
	}

}
