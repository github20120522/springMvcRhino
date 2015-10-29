package xyz.hearthfire.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Created by fz on 2015/10/27.
 */
@Component
public class CacheServiceImpl implements CacheService{

    private static final Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);

    private static int secondUnit = 1000;

    @Autowired
    private EhCacheCacheManager cacheManager;

    @Value("${ehcache.rhino}")
    private String anyCache;

    private Cache cache;

    @PostConstruct
    public void init(){
        cache = cacheManager.getCache(anyCache);
    }

    @Override
    public void putCache(String key, Object value){
        cache.put(key, value);
    }

    @Override
    public void putCache(String key, Object value, long expiredSeconds) {
        long now = System.currentTimeMillis();
        TimerCacheBean timerCacheBean = new TimerCacheBean(value, now + (expiredSeconds * secondUnit));
        cache.put(key, timerCacheBean);
    }

    @Override
    public Object getCache(String key){
        Cache.ValueWrapper element = cache.get(key);
        if(element == null){
            return null;
        }
        Object obj = element.get();
        if(obj instanceof TimerCacheBean){
            TimerCacheBean timerCacheBean = (TimerCacheBean)obj;
            long now = System.currentTimeMillis();
            long timer = timerCacheBean.getTimer();
            // 已过期，则销毁此cache.key，并返回null
            log.debug("当前时间：" + now);
            log.debug("过期时间：" + timer);
            log.debug("时间差异：" + (now - timer));
            if(now > timer){
                cache.evict(key);
                return null;
            }
            return timerCacheBean.getTarget();
        }
        return obj;
    }

    @Override
    public void replaceCache(String key, Object value){
        putCache(key, value);
    }

    @Override
    public void replaceCache(String key, Object value, long expiredSeconds) {
        putCache(key, value, expiredSeconds);
    }

    @Override
    public void removeCache(String key){
        cache.evict(key);
    }

}
