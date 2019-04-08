package com.wsss.frame.dubbo.test.nofile;

import java.io.IOException;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.wsss.frame.dubbo.service.DemoService;
import com.wsss.frame.dubbo.service.impl.DemoServiceImpl;

public class Provider {
	
	private static ApplicationConfig applicationConfig;
	private static RegistryConfig registry;
	    
	public static void main(String[] args) throws IOException {
		applicationConfig=new ApplicationConfig();
        applicationConfig.setName("accountInTest");
        registry=new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        
        ServiceConfig reference = new ServiceConfig();
        Class<?> interfaceClass= DemoService.class;
        //  ApplicationConfig applicationConfig=new ApplicationConfig();
        //  applicationConfig.setName("accountInTest");
        //   RegistryConfig registry = new RegistryConfig();

        reference.setApplication(applicationConfig);
        reference.setInterface(interfaceClass);
        reference.setRef(new DemoServiceImpl());
        reference.setTimeout(30000);
        reference.setRegistry(registry);
        reference.setRetries(0);
        reference.export();
        
        System.in.read();
	}
}
