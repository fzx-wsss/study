package com.wsss.basic.annotation.aliasfor.demo3;

import java.util.Arrays;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

@Test2(test2="123")
public class Test {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(Test.class.getAnnotations()));
		Test2 test2 = Test.class.getAnnotation(Test2.class);
		Test1 test4 = Test.class.getAnnotation(Test1.class);
		System.out.println(test4);
		
		
		Test2 test3 = AnnotationUtils.findAnnotation(Test.class,
				Test2.class);
		Test1 test1 =  AnnotationUtils.findAnnotation(Test.class,
				Test1.class);
		System.out.println(test3.test2());
		System.out.println(test1.test1());

		Test1 test5 =  AnnotatedElementUtils.findMergedAnnotation(Test.class,Test1.class);
		System.out.println(test5.test1());
	}
}
