package com.puvar.cloudservice.service.springcloud.impl;

import com.puvar.cloudcommon.common.model.Task;
import com.puvar.cloudcommon.common.model.TaskHandler;
import org.springframework.stereotype.Service;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/10/25
 * @version : 1.0
 */
@Service
public class MgTaskHandler implements TaskHandler {

    // 执行具体业务逻辑
    @Override
    public void process(Task task) {

    }
}
