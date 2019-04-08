package com.wsss.frame.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;

public class Test {
	public static void main(String[] args) throws NotFoundException {
		ClassPool pool = ClassPool.getDefault();
		//获取要修改的类的所有信息
		CtClass ct = pool.get("com.wsss.frame.javassist.CollectionBase"); 	
		//获取类中的方法
		CtMethod[] cms = ct.getDeclaredMethods();		
		 //获取第一个方法（因为只有一个方法）
		 CtMethod cm = cms[0];		
		// System.out.println("方法名称====" + cm.getName());		 
		 //获取方法信息
		 MethodInfo methodInfo = cm.getMethodInfo();		 
		 //获取类里的em属性
		 CtField cf = ct.getField("em");
		 //获取属性信息
		 FieldInfo fieldInfo = cf.getFieldInfo();  		 
		 System.out.println("属性名称===" + cf.getName());
		 
		 ConstPool cp = fieldInfo.getConstPool();
		   //获取注解信息
		   AnnotationsAttribute attribute2 = new AnnotationsAttribute(cp, AnnotationsAttribute.visibleTag);
		   Annotation annotation = new Annotation("javax.persistence.PersistenceContext", cp);
		 
		   //修改名称为unitName的注解
		   annotation.addMemberValue("unitName", new StringMemberValue("basic-entity", cp));
		   attribute2.setAnnotation(annotation);
		   methodInfo.addAttribute(attribute2);
		   
		   //打印修改后方法
		   Annotation annotation2 = attribute2.getAnnotation("javax.persistence.PersistenceContext");
		   String text = ((StringMemberValue)annotation2.getMemberValue("unitName")).getValue();
		   
		   System.out.println("修改后的注解名称===" + text);


	}
}
