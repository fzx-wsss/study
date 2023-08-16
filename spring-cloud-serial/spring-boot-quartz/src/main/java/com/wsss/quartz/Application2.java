package com.wsss.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

//@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {"com.wsss"})
public class Application2 {
    public static void main(String[] args) {
        // curl -X POST -i http://localhost:18090/themis/actuator/shutdown
        // 关机命令
        new SpringApplicationBuilder(Application2.class)
                .properties("spring.config.location=classpath:/application2.yml").run(args);
    }
}