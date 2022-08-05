package com.wsss.frame.spring.springboot.helloworld;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TestConfiguration {
    static {
        System.out.println("TestConfiguration static");
    }

    @PostConstruct
    public void init() {
        System.out.println("TestConfiguration init");
    }
}
