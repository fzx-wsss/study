package com.wsss.basic.annotation.aliasfor.demo1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAliasAnnotation {
 
    @AliasFor(value = "location")
    String value() default "";
 
    @AliasFor(value = "value")
    String location() default "";

}
