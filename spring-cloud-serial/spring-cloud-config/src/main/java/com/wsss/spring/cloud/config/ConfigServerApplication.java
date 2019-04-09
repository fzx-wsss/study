package com.wsss.spring.cloud.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;


@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
	public static void main(String[] args) {
//		SpringApplication.run(ConfigServerApplication.class, args);
		 new SpringApplicationBuilder(ConfigServerApplication.class)
			.properties("spring.config.location=classpath:/configServer.yml").run(args);
	}
}