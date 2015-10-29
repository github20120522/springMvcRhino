package xyz.hearthfire.cache;

/**
 * Created by fz on 2015/10/29.
 */
public interface CacheService{

    void putCache(String key, Object value);

    void putCache(String key, Object value, long expiredSeconds);

    Object getCache(String key);

    void replaceCache(String key, Object value);

    void replaceCache(String key, Object value, long expiredSeconds);

    void removeCache(String key);
}
