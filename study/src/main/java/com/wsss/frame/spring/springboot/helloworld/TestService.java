package com.wsss.frame.spring.springboot.helloworld;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TestService {
    static {
        System.out.println("Service static");
    }

    @PostConstruct
    public void init() {
        System.out.println("Service init");
    }
}
