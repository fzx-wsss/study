package com.wsss.frame.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wsss.frame.dubbo.model.User;
import com.wsss.frame.dubbo.service.DemoService;

public class DemoServiceImpl implements DemoService {

	public String sayHello(String name) {
		return "Hello " + name;
	}

	@Override
	public User create(User user) {
		System.out.println(user.toString());
		User user2 = new User();
		user2.setAge(1);
		user2.setName("1");
		user2.setSex("man");
		return user2;
	}

	public List getUsers() {
		List list = new ArrayList();
		User u1 = new User();
		u1.setName("jack");
		u1.setAge(20);
		u1.setSex("男");

		User u2 = new User();
		u2.setName("tom");
		u2.setAge(21);
		u2.setSex("女");

		User u3 = new User();
		u3.setName("rose");
		u3.setAge(19);
		u3.setSex("女");

		list.add(u1);
		list.add(u2);
		list.add(u3);
		return list;
	}
}