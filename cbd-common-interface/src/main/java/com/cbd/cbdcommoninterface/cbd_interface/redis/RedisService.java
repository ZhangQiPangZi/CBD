package com.cbd.cbdcommoninterface.cbd_interface.redis;

import com.cbd.cbdcommoninterface.keys.KeyPrefix;

import java.util.Map;

public interface RedisService {
    /**
     * 获取单个对象
     *
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(KeyPrefix prefix, String key, Class<T> clazz);

    /**
     * 设置对象
     *
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    <T> boolean set(KeyPrefix prefix, String key, T value);

    /**
     * 判断key是否存在
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    <T> boolean exists(KeyPrefix prefix, String key);

    /**
     * 增加值，原子操作
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key);

    /**
     * 减少值，原子操作
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    <T> Long decr(KeyPrefix prefix, String key);

    /**
     * 删除缓存
     *
     * @param prefix
     * @param key
     * @return
     */
    boolean delete(KeyPrefix prefix, String key);

    /**
     * redis存储map
     * @param key
     * @param map
     * @param <T>
     * @return
     */
    <T> Boolean hmset(KeyPrefix prefix, String key, Map<String,T> map);

    /**
     * redis获取map
     * @param key
     * @param <T>
     * @return
     */
    <T> Map<String, T> hmget(KeyPrefix prefix, String key, Class<T> clazz);
}
