package com.wsss.frame.mybatis.generator;

import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class CommentGenerator implements org.mybatis.generator.api.CommentGenerator{

	public void addClassComment(InnerClass arg0, IntrospectedTable arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addClassComment(InnerClass arg0, IntrospectedTable arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	public void addComment(XmlElement arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addConfigurationProperties(Properties arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addEnumComment(InnerEnum arg0, IntrospectedTable arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addFieldComment(Field arg0, IntrospectedTable arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addFieldComment(Field field, IntrospectedTable arg1, IntrospectedColumn introspectedColumn) {
		if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
	        field.addJavaDocLine("/** " + introspectedColumn.getRemarks() + " */");
	    }
		
	}

	public void addGeneralMethodComment(Method arg0, IntrospectedTable arg1) {
		// TODO Auto-generated method stub
		
	}

	public void addGetterComment(Method arg0, IntrospectedTable arg1, IntrospectedColumn arg2) {
		// TODO Auto-generated method stub
		
	}

	public void addJavaFileComment(CompilationUnit arg0) {
		
	}

	public void addRootComment(XmlElement arg0) {
		// TODO Auto-generated method stub
		
	}

	public void addSetterComment(Method arg0, IntrospectedTable arg1, IntrospectedColumn arg2) {
		// TODO Auto-generated method stub
		
	}

}
