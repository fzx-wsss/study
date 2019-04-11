package com.wsss.spring.cloud.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@FeignClient("spring-boot-client")
public interface OrderService {
    
    /**
     * http： 这里是写member-server里面的访问路径。 getMember是访问方法 
     * @return
     */
	@RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String get();

}