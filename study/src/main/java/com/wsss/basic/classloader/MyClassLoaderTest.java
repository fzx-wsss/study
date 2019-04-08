package com.wsss.basic.classloader;

import java.lang.reflect.Field;

public class MyClassLoaderTest {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ClassLoader myLoaderA=new MyClassLoader();   
		Object objA= myLoaderA.loadClass("com.wsss.basic.classloader.MyBean").newInstance();    
		Object objB=new MyBean(); 
        
        System.out.println(objA.getClass());  
        System.out.println(objB.getClass());  
        System.out.println(objA.getClass().getClassLoader());  
        System.out.println(objB.getClass().getClassLoader());  
        System.out.println(objA.getClass().equals(objB.getClass()));
       
        System.out.println("-----------------------------------------------------------------------");
        
        Object obja = getBean(objA);
        Object objb = getBean(objB);
        
        System.out.println(obja.getClass());  
        System.out.println(objb.getClass());  
        System.out.println(obja.getClass().getClassLoader());  
        System.out.println(objb.getClass().getClassLoader());  
        System.out.println(obja.getClass().equals(objb.getClass()));
	}
	
	private static Object getBean(Object obj) throws IllegalArgumentException, IllegalAccessException {
		Class clazz = obj.getClass();
		Field field = clazz.getDeclaredFields()[0];
		field.setAccessible(true);
		return field.get(obj);
	}
}
