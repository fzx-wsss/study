package com.wsss.basic.generic.classify;

public class Main {
	public static void main(String[] args) {
		Event event = new EndEvent();
		Mylistener<Event> listener = getListener();
		listener.listen(event);
	}
	
	public static Mylistener getListener() {
		return new EndListener();
	}
}
