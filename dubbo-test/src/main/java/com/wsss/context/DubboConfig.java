package com.wsss.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;

/**
 * @Description: dubbo基础配置
 * @see: DubboConfig 此处填写需要参考的类
 * @version 2014年7月31日 下午3:07:15
 * @author jian.zhang
 */
@Configuration
@PropertySource("classpath:/dubbo.properties")
public class DubboConfig {

	@Value("${dubbo.resAddress}")
	private String resAddress;


	@Bean
	public ApplicationConfig application() {
		ApplicationConfig applicationConfig = new ApplicationConfig("");
		//applicationConfig.setMonitor(monitor());
		applicationConfig.setRegistry(registry());
		return applicationConfig;
	}

	@Bean
	public RegistryConfig registry() {
		RegistryConfig registryConfig = new RegistryConfig(resAddress);
		registryConfig.setProtocol("dubbo");
		registryConfig.setPort(2181);
		return registryConfig;
	}

}
