package com.kaishengit;


import com.google.common.collect.Lists;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Test {

    public static void main(String[] args) throws InterruptedException, IOException {



       /* MemcachedClient memcachedClient = new MemcachedClient(AddrUtil.getAddresses("127.0.0.1:11211"));
        memcachedClient.add("name",10,"Java");

        memcachedClient.delete("name");

        String name = (String) memcachedClient.get("name");
        System.out.println(name);*/





        CacheManager cacheManager = new CacheManager();

        Ehcache ehcache = cacheManager.getEhcache("simpleCache");
        Element element = new Element("name","Java");
        ehcache.put(element);

        ehcache.remove("name");

        //------------------------

        Element element1 = ehcache.get("name");
        System.out.println(element1.getObjectValue());


    }

}
