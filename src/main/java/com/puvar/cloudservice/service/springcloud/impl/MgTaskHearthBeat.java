package com.puvar.cloudservice.service.springcloud.impl;

import com.puvar.cloudcommon.common.model.HeartBeat;
import com.puvar.cloudcommon.common.util.DateUtil;
import com.puvar.cloudservice.common.client.RedisClient;
import com.puvar.cloudservice.common.constants.MgTaskConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/10/25
 * @version : 1.0
 */
@Service
@Slf4j
public class MgTaskHearthBeat implements HeartBeat {

    @Autowired
    private RedisClient redisClient;

    @Override
    public String getLastBeatValue(String healthBeatKey) {
        try {
            return redisClient.getStringWithRetry(healthBeatKey, 3);
        } catch (Exception e) {
            log.error("get heart beat value exception:{}", ExceptionUtils.getFullStackTrace(e));
            return null;
        }
    }

    @Override
    public boolean beat(String heartBeatKey, String heartBeatValue) {
        for (int i = 0; i < 3; i++) {
            try {
                redisClient.getAndSetString(heartBeatKey, DateUtil.format(new Date(), "yyyyMMddHHmmss"));
                redisClient.expire(heartBeatKey, MgTaskConstants.DEF_TASK_HEART_BEAT_OVERTIME * 1000, TimeUnit.MILLISECONDS);
                return true;
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }
}
