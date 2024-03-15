package com.wsss.frame.guava;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;

public class CaffeineTest {
    private static LoadingCache<String, String> cache = Caffeine.newBuilder()
            .initialCapacity(20_0000)
            .maximumSize(20_0000)
            .expireAfterWrite(Duration.ofMinutes(10))
            .build(new CacheLoader<String, String>() {
                @Nullable
                @Override
                public String load(@Nonnull String key) throws Exception {
                    if ("null".equals(key)) {
                        System.out.println("key is null");
                        return null;
                    }
                    return key;
                }
            });

    public static void main(String[] args) {
        try {
            System.out.println(cache.get("null"));
            System.out.println(cache.get("null"));
        }catch (UnsupportedOperationException e) {
            System.out.println("UnsupportedOperationException");
        }
    }
}
