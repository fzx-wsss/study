package com.wsss.basic.generic.classify;

public class EndListener implements Mylistener<EndEvent>{

	@Override
	public void listen(EndEvent t) {
		// TODO Auto-generated method stub
		System.out.println("EndListener");
		t.endEvent();
	}


}
