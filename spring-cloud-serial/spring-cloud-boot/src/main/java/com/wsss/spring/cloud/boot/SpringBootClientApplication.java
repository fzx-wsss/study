package com.wsss.spring.cloud.boot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class SpringBootClientApplication {

	public static void main(String[] args) {
		// SpringApplication.run(SpringBootClientApplication.class, args);
		new SpringApplicationBuilder(SpringBootClientApplication.class)
				.properties("spring.config.location=classpath:/application.yml").run(args);
	}

	private String hello = "hello";

	@RequestMapping(value = "/hello")
	public String hello() throws InterruptedException {
		System.out.println("ok");
		return hello;
	}

	@RequestMapping(value = "/redirect")
	public RedirectView redirect() throws InterruptedException {
		System.out.println("redirect");
		// Thread.sleep(5000);
		return new RedirectView("/spring-boot-client/hello", true);
	}

	@RequestMapping(value = "/**")
	public String hello2(HttpServletRequest request) throws InterruptedException {
		System.out.println(request.getRequestURI());
		return hello;
	}
}