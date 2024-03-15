package com.wsss.frame.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class LoadingCacheTest {
    private static LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            //note：如这里使用了expireAfterWrite,那么如果在异步刷新返回结果前数据过期，依旧会在加载数据的阻塞get方法
            .expireAfterAccess(9, TimeUnit.SECONDS)
            .maximumSize(30000)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws InterruptedException {
                    if ("null".equals(key)) {
                        throw new UnsupportedOperationException();
                    }
                    return key;
                }
            });

    public static void main(String[] args) throws ExecutionException {
        try {
            cache.get("null");
        }catch (UnsupportedOperationException e) {
            System.out.println("UnsupportedOperationException");
        }

    }
}
