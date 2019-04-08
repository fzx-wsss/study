package com.wsss.basic.generic.classify;

public interface Mylistener<T extends Event> {
	void listen(T t);
}
