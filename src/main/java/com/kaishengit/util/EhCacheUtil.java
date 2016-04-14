package com.kaishengit.util;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EhCacheUtil {

    private static CacheManager cacheManager = new CacheManager();


    public static void set(String templateName,String key,Object value) {
        Ehcache ehcache = cacheManager.getEhcache(templateName);
        Element element = new Element(key,value);
        ehcache.put(element);
    }

    public static Object get(String templateName,String key) {
        Ehcache ehcache = cacheManager.getEhcache(templateName);
        Element element = ehcache.get(key);
        if(element == null) {
            return null;
        } else {
            return element.getObjectValue();
        }
    }

}
