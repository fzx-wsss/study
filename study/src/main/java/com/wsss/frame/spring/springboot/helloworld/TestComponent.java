package com.wsss.frame.spring.springboot.helloworld;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestComponent {
    static{
        System.out.println("Component static");
    }

    @PostConstruct
    public void init() {
        System.out.println("Component init");
    }
}
