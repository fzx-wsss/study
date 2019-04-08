package com.wsss.frame.spring.custom.element;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class TestNamespaceHandler extends NamespaceHandlerSupport {  
    public void init() {  
        registerBeanDefinitionParser("user", new UserBeanDefinitionParser());  
    }  
}