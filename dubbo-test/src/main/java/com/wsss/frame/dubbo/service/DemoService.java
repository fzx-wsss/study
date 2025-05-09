package com.wsss.frame.dubbo.service;

import com.wsss.frame.dubbo.model.User;

import java.util.List;

public interface DemoService {
	String sayHello(String name);

    User create(User user);
	  
    public List getUsers();  
}
