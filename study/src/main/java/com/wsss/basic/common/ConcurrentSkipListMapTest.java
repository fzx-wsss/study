package com.wsss.basic.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1000);
        list.add(1003);
        Map<Integer,Integer> map = new ConcurrentSkipListMap();
        int start  = 1000;
        int end = 2000;
        int current = list.get(0);
        for(int i=list.size()-1;i>-1;i--) {
            if(list.get(i) < start) {
                current = list.get(i);
                break;
            }
            map.put(list.get(i),list.get(i));
        }

        for(int i=start;i<= end;i++) {
            if(map.containsKey(i)) {
                current = i;
                continue;
            }
            map.put(i,current);
        }
        System.out.println(map.size());
        System.out.println(map);
    }
}
