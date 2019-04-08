package com.wsss.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
/**
 * 
 * @Description: dubbo提供者配置
 * @see: DubboServiceConfig 此处填写需要参考的类
 * @version 2014年7月31日 下午3:08:52 
 * @author jian.zhang
 */
@Configuration
@ImportResource("classpath:dubbo-service.xml")
public class DubboServiceConfig {
	
}
