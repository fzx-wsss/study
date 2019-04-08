package com.wsss.basic.annotation.aliasfor.demo2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@AnnotaionBase
public @interface AnnotationChild {
 
    @AliasFor(annotation = AnnotaionBase.class, attribute = "value")
    String extendValue() default "AnnotationChild";
}