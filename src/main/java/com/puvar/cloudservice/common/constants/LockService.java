package com.puvar.cloudservice.common.constants;

import com.puvar.cloudservice.common.client.RedisClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * redis实现分布式锁
 * @Auther: dingyuanmeng
 * @Date: 2019/8/14
 * @version : 1.0
 */
@Service
public class LockService {

    private static Logger logger = LoggerFactory.getLogger(LockService.class);

    @Autowired
    private RedisClient redisClient;

    private ThreadLocal<String> tlLoclValue = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return System.currentTimeMillis() + "";
        }
    };

    public static final long defaultTimeout = 60000L;

    /**
     * redis加锁
     *
     * @param lockKey 锁名
     * @param howlong 超时时间，单位ms
     * @return
     */
    public boolean lock(String lockKey, Long howlong) {
        if (StringUtils.isBlank(lockKey)) {
            throw new IllegalArgumentException("lockName must not be blank");
        }
        if (null == howlong || 1 >= howlong) {
            howlong = defaultTimeout;
        }
        // 采用系统时间戳为锁的值
        String lockValue = System.currentTimeMillis() + "";
        try {
            boolean ret = redisClient.setStringIfAbsent(lockKey, lockValue);
            if (ret) {
                tlLoclValue.set(lockValue);
                logger.info("get lock success, lockKey:{}", lockKey);
                return true;
            }
            // 如果设置失败，判断锁是否超时
            logger.info("get lock fail, check timeout");
            String oldValue = redisClient.getString(lockKey);
            if (null == oldValue) {
                logger.info("get oldValue is null, get lock fail");
                return false;
            }
            long diff = System.currentTimeMillis() - Long.valueOf(oldValue);
            logger.info("first diff:{}, howlong:{}", diff, howlong);
            if (diff > howlong) {  // 超时了
                logger.warn("{} lock time out, this get lock", lockKey);
                lockValue = System.currentTimeMillis() + "";
                oldValue = redisClient.getAndSetString(lockKey, lockValue);
                diff = System.currentTimeMillis() - Long.valueOf(oldValue);
                logger.info("second diff:{}, howlong:{}", diff, howlong);
                if (diff > howlong) {
                    tlLoclValue.set(lockValue);
                    logger.info("timeout then get lock success, lockKey:{}", lockKey);
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("get lock exception, lockKey:{}, e:{}", lockKey);
        }
        logger.info("get lock fail, lockKey:{}", lockKey);
        return false;
    }

    public boolean lock(String lockKey, Long howlong, int retry) {
        for (int i = 0; i < retry; i++) {
            if (lock(lockKey, howlong)) {
                return true;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                logger.error(ExceptionUtils.getMessage(e));
            }
        }
        return false;
    }

    public void unlock(String lockKey) {
        if (StringUtils.isBlank(lockKey)) {
            throw new IllegalArgumentException("lockName must not be blank");
        }
        try {
            String timestamp = redisClient.getString(lockKey);
            if (StringUtils.isNotEmpty(timestamp) && timestamp.equals(tlLoclValue.get())) {
                redisClient.delete(lockKey);
                logger.info("unlock success, lockName:{}", lockKey);
            } else {
                logger.warn("lock already release, lockName:{}", lockKey);
            }
        } catch (Exception e) {
            logger.error("unlock {} error, ex:{}", lockKey, e);
        }
    }
}
