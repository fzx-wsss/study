package com.wsss.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@EnableZuulProxy
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ZuulServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulServiceApplication.class, args);
//		 new SpringApplicationBuilder(ZuulServiceApplication.class)
//			.properties("spring.config.location=classpath:/application.yml").run(args);
	}
}