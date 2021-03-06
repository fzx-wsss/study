package com.wsss.basic.annotation.aliasfor.demo2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//编写元注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AnnotaionBase {
    String value() default "AnnotaionBase";
}