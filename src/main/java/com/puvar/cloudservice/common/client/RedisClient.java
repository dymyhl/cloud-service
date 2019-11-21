package com.puvar.cloudservice.common.client;

import com.alibaba.fastjson.JSONObject;
import com.puvar.cloudcommon.common.util.JacksonUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis操作
 */
@Component
public class RedisClient {

    // 字符串操作
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public String hget(String key, String field) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        return opsForHash.get(key, field);
    }

    public Map<String, String> hgetAll(String key) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        return opsForHash.entries(key);
    }

    public void hset(String key, Map<String, String> hashMap) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        opsForHash.putAll(key, hashMap);
    }

    public void expire(String key, long timeout, TimeUnit unit) {
        stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 批量查询
     */
    public Map<String, JSONObject> getBatchJsonObject(final List<String> queryList) {
        if (CollectionUtils.isEmpty(queryList)) {
            return null;
        }
        final RedisSerializer<String> seria = stringRedisTemplate.getStringSerializer();
        RedisCallback<List<Object>> action = new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (int i = 0; i < queryList.size(); i++) {
                    connection.get(seria.serialize(queryList.get(i)));
                }
                return connection.closePipeline();
            }
        };
        List<Object> execute = stringRedisTemplate.execute(action);
        if (CollectionUtils.isEmpty(execute)) {
            return null;
        }
        Map<String, JSONObject> resultMap = new HashMap<String, JSONObject>();
        for (int i = 0; i < execute.size(); i++) {
            if (execute.get(i) instanceof byte[]) {
                String resultStr = seria.deserialize((byte[]) execute.get(i));
                resultMap.put(queryList.get(i), JSONObject.parseObject(resultStr));
            }
        }
        return resultMap;
    }

    /**
     * 批量删除
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public void delete(List<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    public void setString(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, value.toString());
    }

    /**
     * 批量插入
     *
     * @param map
     */
    public void insertBatch(final Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return;
        }
        final RedisSerializer<String> seria = stringRedisTemplate.getStringSerializer();
        RedisCallback<List<Object>> action = new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    connection.set(seria.serialize(entry.getKey()),
                            seria.serialize(JacksonUtil.toJson(entry.getValue())));
                }
                return connection.closePipeline();
            }
        };
        stringRedisTemplate.execute(action);
    }

    public boolean setStringIfAbsent(String key, String value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public String getAndSetString(String key, String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    public String getStringWithRetry(String key, int retry) {
        for (int i = 0; i < retry; i++) {
            try {
                return (String) stringRedisTemplate.opsForValue().get(key);
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }
}
