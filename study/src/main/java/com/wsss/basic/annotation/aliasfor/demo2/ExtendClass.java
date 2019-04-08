package com.wsss.basic.annotation.aliasfor.demo2;

import org.springframework.core.annotation.AnnotatedElementUtils;

@AnnotationChild(extendValue = "extendValue")
public class ExtendClass {
	public static void main(String[] args) {
		AnnotaionBase annotaionBase = AnnotatedElementUtils.findMergedAnnotation(ExtendClass.class, AnnotaionBase.class);
        System.out.println(annotaionBase.value());
	}
}