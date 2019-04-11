package com.wsss.spring.cloud.fegin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import feign.Feign;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringFeginClientApplication {
	@Autowired
	private OrderService orderService;
	
	public static void main(String[] args) {
		//SpringApplication.run(SpringBootClientApplication.class, args);
		Feign feign = null;
		 new SpringApplicationBuilder(SpringFeginClientApplication.class)
			.properties("spring.config.location=classpath:/application.yml").run(args);
	}

	@RequestMapping(value = "/hello")
	public String hello() {
		return orderService.get();
	}
	
	/**
     * 表示支持负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}