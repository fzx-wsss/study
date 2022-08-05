package com.wsss.basic.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class MapTest {
    private static int n = 10000000;
    public static void main(String[] args) {
        Map hashMap = new HashMap<>();
        Map concurrentHashMap = new ConcurrentHashMap();
        testMap(hashMap,"hashMap","0");
        testMap(concurrentHashMap,"concurrentHashMap","0");

        testMap(concurrentHashMap,"concurrentHashMap","0");
        testMap(hashMap,"hashMap","0");

        randomPut(hashMap,1000);
        randomPut(concurrentHashMap,1000);

        testMap(hashMap,"hashMap","1000");
        testMap(concurrentHashMap,"concurrentHashMap","1000");

        testMap(concurrentHashMap,"concurrentHashMap","1000");
        testMap(hashMap,"hashMap","1000");
    }

    private static void randomPut(Map map,int num) {
        for(int i=0;i<n;i++) {
            map.put(String.valueOf(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE)),"");
        }
    }

    private static void testMap(Map map,String name,String num) {
        for(int i=0;i<n;i++) {
            map.get("1234567890");
        }
        long time = System.currentTimeMillis();
        for(int i=0;i<n;i++) {
            map.get("1234567890");
        }
        System.out.println(name +":"+ num +":"+ (System.currentTimeMillis()-time));
    }

}
