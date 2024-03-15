package com.wsss.frame.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.sql.DatabaseMetaData;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LoadCacheTest {

    public static void main(String[] args) throws Exception {
        LoadingCache<String, String> asynCache = CacheBuilder.newBuilder()
                //note：如这里使用了expireAfterWrite,那么如果在异步刷新返回结果前数据过期，依旧会在加载数据的阻塞get方法
                .expireAfterWrite(9,TimeUnit.SECONDS)
                //refreshAfterWrite asyncReloading 数据加载时候直接返回老数据+数据更新会就能取到新数据
                .refreshAfterWrite(5, TimeUnit.SECONDS).build(CacheLoader.asyncReloading(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws InterruptedException {
                        TimeUnit.SECONDS.sleep(3);
                        return new Random().nextInt(100) + "";
                    }
                }, new ThreadPoolExecutor(1, 2, 1000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("async-symbol-limit-thread-%d").build())));

        System.out.println("start:" + new Date());
        for (int i = 0; i < 20; i++) {
            System.out.println(asynCache.get("1") + new Date());
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("end:" + new Date());
    }
}
