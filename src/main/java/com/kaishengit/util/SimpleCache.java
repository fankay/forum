package com.kaishengit.util;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 一个简单的缓存实现
 */
public class SimpleCache {

    private static Map<String,Object> cache = Maps.newHashMap();

    public static void put(String key,Object value) {
        cache.put(key,value);
    }

    public static Object get(String key) {
        return cache.get(key);
    }


}
