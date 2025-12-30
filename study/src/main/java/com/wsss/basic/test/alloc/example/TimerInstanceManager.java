package com.wsss.basic.test.alloc.example;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimerInstanceManager {
    private final Map<String, Map<String, Timer>> timerTagMap = new CacheMap<>(k -> new CacheMap<>(t -> createTimer()));

    private final Table<String, String, Timer> timerTable = HashBasedTable.create();

    public void record(String key, String tag, long cost) {
//        System.out.println("key: " + key + ", cost: " + cost);
//        Timer timer = get(key,"");
        Timer timer;
        synchronized (timerTable) {
            timer = timerTable.get(key, tag);
        }
        if(timer!=null) {
            timer.purge();
        }
    }


    private Timer createTimer() {
        return new Timer();
    }
}
