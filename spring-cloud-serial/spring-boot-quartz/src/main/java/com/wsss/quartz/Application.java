package com.wsss.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {"com.wsss"})
public class Application {
    public static void main(String[] args) {
        // curl -X POST -i http://localhost:18090/themis/actuator/shutdown
        // 关机命令
        SpringApplication.run(Application.class);
    }
}