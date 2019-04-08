package com.wsss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;

@Controller
@RequestMapping("/dubbo")
public class HttpController {
	private static ApplicationConfig applicationConfig;
	private static RegistryConfig registry;
	static {
		applicationConfig=new ApplicationConfig();
        applicationConfig.setName("accountInTest");
        registry=new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public Object test(String name) {
		return "hello" + name;
	}
	
	@RequestMapping("/{service}/{method}")
	@ResponseBody
	public Object hello(String service,String method,String name) {
		System.out.println(service);
		System.out.println(method);
		System.out.println(name);
		return genericInvoke(service, method, new Object[] {name});
	}
	
	public Object genericInvoke(String interfaceClass, String methodName, Object[] parameters){
		
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(applicationConfig); 
        reference.setRegistry(registry); 
        reference.setInterface(interfaceClass); // 接口名 
        reference.setGeneric(true); // 声明为泛化接口 
        
        //ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //API方式编程时，容易忽略此问题。
        //这里使用dubbo内置的简单缓存工具类进行缓存
        
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference); 
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用 

        String[] invokeParamTyeps = new String[parameters.length];
        for(int i = 0; i < parameters.length; i++){
            invokeParamTyeps[i] = parameters[i].getClass().getTypeName();
        }
        return genericService.$invoke(methodName, invokeParamTyeps, parameters);
    }
}
