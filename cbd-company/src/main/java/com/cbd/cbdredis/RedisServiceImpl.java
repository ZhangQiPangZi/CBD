package com.cbd.cbdredis;

import com.alibaba.fastjson.JSON;
import com.cbd.cbdcommoninterface.cbd_interface.redis.RedisService;
import com.cbd.cbdcommoninterface.keys.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取单个对象
     *
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
//            生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     *
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    @Override
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.equals("")) {
                return false;
            }

            String realKey = prefix.getPrefix() + key;
            int seconds = prefix.expireSeconds();
//            如果等于0，说明这个key是被永久保存的
            if (seconds == 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    @Override
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值，原子操作
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    @Override
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值，原子操作
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    @Override
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 删除缓存
     *
     * @param prefix
     * @param key
     * @return
     */
    @Override
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            long res = jedis.del(realKey);
            return res > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * redis存储map
     * @param key
     * @param map
     * @param <T>
     * @return
     */
    @Override
    public <T> Boolean hmset(KeyPrefix prefix, String key, Map<String,T> map) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            Map<String, String> realMap = new HashMap<>();
            for(String k : map.keySet()){
                realMap.put(k, beanToString(map.get(k)));
            }
            String realKey = prefix.getPrefix() + key;
            jedis.hmset(realKey, realMap);
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * redis获取map
     * @param key
     * @param <T>
     * @return
     */
    @Override
    public <T> Map<String, T> hmget(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Map<String, T> resmap = new HashMap<>();

            String realKey = prefix.getPrefix() + key;
            Map<String, String> temp = jedis.hgetAll(realKey);

            for(String k : temp.keySet()){
                resmap.put(k, stringToBean(temp.get(k),clazz));
            }

            return resmap;
        } finally {
            returnToPool(jedis);
        }
    }

    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }

        Class<?> clazz = value.getClass();
        if (clazz == Integer.class) {
            return "" + value;
        }
        if (clazz == String.class) {
            return (String) value;
        }

        return JSON.toJSONString(value);
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.equals("") || clazz == null) {
            return null;
        }

        if (clazz == String.class) {
            return (T) str;
        }
        if (clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        }
        if (clazz == Float.class){
            return (T)Float.valueOf(str);
        }
        if (clazz == Double.class){
            return (T)Double.valueOf(str);
        }

        return JSON.toJavaObject(JSON.parseObject(str), clazz);
    }

    private void returnToPool(Jedis jedis) {
        //            实际上不会关闭，而是返回至连接池中
        if (jedis != null) {
            jedis.close();
        }
    }


}