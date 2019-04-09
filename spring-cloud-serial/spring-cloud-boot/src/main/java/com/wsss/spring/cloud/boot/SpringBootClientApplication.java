package com.wsss.spring.cloud.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringBootClientApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SpringBootClientApplication.class, args);
		 new SpringApplicationBuilder(SpringBootClientApplication.class)
			.properties("spring.config.location=classpath:/application.yml").run(args);
	}

	@Value("${hello}")
	String hello;

	@RequestMapping(value = "/hello")
	public String hello() {
		return hello;
	}
}