package com.wsss.basic.common;

class A {
	   public static void show() {
	      System.out.println("Class A show() function");
	   }
	}

	class B extends A {
	   public static void show() {
	      System.out.println("Class B show() function");
	   }
	}

	public class ClassDemo {

	   public static void main(String[] args) {
	        
	     ClassDemo cls = new ClassDemo();
	     Class c = cls.getClass();      
	     System.out.println("1:" + c);  
	   
	     Object obj = new A();        
	     B b1 = new B();
	     b1.show();
	        
	     // casts object
	     Object a = B.class.cast(obj);
	   
	     System.out.println("2:" + obj.getClass());
	     System.out.println("3:" + b1.getClass());
	     System.out.println("4:" + a.getClass());               
	   }
	}