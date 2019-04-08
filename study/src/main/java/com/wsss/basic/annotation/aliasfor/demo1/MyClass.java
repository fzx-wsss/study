package com.wsss.basic.annotation.aliasfor.demo1;

import org.springframework.core.annotation.AnnotationUtils;

public class MyClass {
 
    @MyAliasAnnotation(location = "这是值")
    public static void one(){
    }
 
    @MyAliasAnnotation(location = "这是值")
    public static void one2(){
    }
    
    public static void main(String[] args) throws NoSuchMethodException, SecurityException {
 
        MyAliasAnnotation aliasAnnotation = AnnotationUtils.findAnnotation(MyClass.class.getMethod("one"), MyAliasAnnotation.class);
       System.out.println(aliasAnnotation);
 
        MyAliasAnnotation aliasAnnotation2 = AnnotationUtils.findAnnotation(MyClass.class.getMethod("one2"), MyAliasAnnotation.class);
        System.out.println(aliasAnnotation2);
	}

}