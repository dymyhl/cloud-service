package com.puvar.cloudservice.domain;

import com.puvar.cloudcommon.common.model.Task;
import com.puvar.cloudservice.common.utils.DateUtil;
import com.puvar.cloudservice.service.springcloud.impl.MgTaskHandler;
import com.puvar.cloudservice.service.springcloud.impl.MgTaskHearthBeat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.concurrent.ExecutorService;

/***
 * MgTask任务
 * @Auther: dingyuanmeng
 * @Date: 2019/10/25
 * @version : 1.0
 */
@Slf4j
public class MgTaskTask extends Task {

    private String heartBeatKey;
    private MgTaskHandler mgTaskHandler;
    private MgTaskHearthBeat heartBeat;
    private Date createTime;
    private ExecutorService executor;

    @Override
    public void executor() {
        try {

        } catch (Exception e) {
            log.error("MgTaskTask executor is error {}", e);
            if (this.isRetry()) {
                this.retry();
            } else {
                this.fail();
            }
        }
    }

    @Override
    public boolean acquireGlobalLock() {
        if (!heartBeat.beat(heartBeatKey, DateUtil.format(new Date(), "yyyyMMddHHmmss"))) {
            setRetry(true);
            log.error("acquireGlobalLock set heart beat fail, taskId:{}", getTaskId());
            return false;
        }
        return true;
    }

    @Override
    public boolean fail() {
        // 表中设置状态标识，直接失败
        return false;
    }

    @Override
    public boolean retry() {
        // 表中设置状态标识，可下次重试
        return false;
    }

    @Override
    public boolean success() {
        // 表中设置状态标识，执行成功
        return false;
    }

    @Override
    public boolean checkExtParams() {
        if (StringUtils.isEmpty(heartBeatKey) || createTime == null
                || StringUtils.isEmpty(getParams().get("name").toString())
                || StringUtils.isEmpty(getParams().get("age").toString())) {
            return false;
        }
        return true;
    }
}
