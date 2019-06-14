package com.wsss.security.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class SpringBootClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootClientApplication.class, args);
		/*
		 * new SpringApplicationBuilder(SpringBootClientApplication.class)
		 * .properties("spring.config.location=classpath:/application.yml").run(
		 * args);
		 */
	}

	@RequestMapping(value = "/resource/hello")
	public String hello() {
		return "world";
	}
}