package com.wsss.basic.test.alloc.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 不是public类，只提供给本包内的使用
 * share包中提供了公共的类
 * @param <K>
 * @param <V>
 */
class CacheMap<K, V> extends ConcurrentHashMap<K, V> {
    private Function<? super K, ? extends V> mappingFunction;

    public CacheMap(Function<? super K, ? extends V> mappingFunction) {
        this.mappingFunction = mappingFunction;
    }

    @Override
    public V get(Object key) {
        V v = super.get(key);
        if (v == null) {
            v = this.computeIfAbsent((K) key, mappingFunction);
        }
        return v;
    }
}