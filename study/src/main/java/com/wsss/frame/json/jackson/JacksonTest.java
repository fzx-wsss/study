package com.wsss.frame.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.ArrayList;
import java.util.List;

public class JacksonTest {
    public static void main(String[] args) throws Exception {
        ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addDeserializer(Object.class,new DemoDeserializer());
//        simpleModule.addKeyDeserializer(Object.class,new DemoKeyDeserializer());
////        simpleModule.addSerializer(Object.class,new DemoSerializer());
////        simpleModule.addKeySerializer(Object.class,new DemoSerializer());
//        OBJECT_MAPPER.registerModule(simpleModule);
        OBJECT_MAPPER.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        List<Demo> list = new ArrayList<>();
        Demo demo = new Demo();
        demo.setName("123");
        list.add(demo);
        String json = OBJECT_MAPPER.writeValueAsString(list);
        System.out.println(json);

        List<Demo> res = OBJECT_MAPPER.readValue(json,List.class);
        System.out.println(res);
    }
}
