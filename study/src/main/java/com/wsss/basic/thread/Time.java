package com.wsss.basic.thread;

/**
 * 参考TimeUnit
 * @author Administrator
 *
 */
public enum Time {
	TIME("1","2") {
		public void test() {
			System.out.println(getName());
		}
	};
	public String name;
	public String status;
	
	private Time(String name,String status) {  
        this.name = name;  
        this.status = status;
    }
	
	
	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}
	public abstract void test();
	
	public void sleep() {
		test();
	}
}
