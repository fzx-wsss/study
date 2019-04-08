package com.wsss.basic.annotation.aliasfor.demo3;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Test1
public @interface Test2 {
	@AliasFor(annotation=Test1.class,value="test1")
	String test2() default "test2";
}
