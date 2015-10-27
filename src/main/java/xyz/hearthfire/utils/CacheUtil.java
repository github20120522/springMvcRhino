package xyz.hearthfire.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Created by fz on 2015/10/27.
 */
@Component
public class CacheUtil{

    @Autowired
    private CacheManager cacheManager;

    @Value("${ehcache.rhino}")
    private String anyCache;

    private Cache cache;

    @PostConstruct
    public void init(){
        cache = cacheManager.getCache(anyCache);
    }

    public void putCache(String key, Object value){
        Element element = new Element(key, value);
        cache.put(element);
    }

    public Object getCache(String key){
        Element element = cache.get(key);
        if(element == null){
            return null;
        }else{
            Object value = element.getObjectValue();
            return value;
        }
    }

    public void replaceCache(String key, Object value){
        putCache(key, value);
    }

    public void removeCache(String key){
        cache.remove(key);
    }
}
