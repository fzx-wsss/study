package com.wsss.frame.spring.springboot.converter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/helloenum")
    @ResponseBody
    public String helloworld(@ModelAttribute("person") Person person) {
        return "helloenum:" + person.toString();
    }
}