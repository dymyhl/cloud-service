package com.puvar.cloudservice.service.springcloud.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.puvar.cloudcommon.common.model.Task;
import com.puvar.cloudcommon.common.model.TaskExecutor;
import com.puvar.cloudservice.common.constants.MgTaskConstants;
import com.puvar.cloudservice.dao.springcloud.MgTaskMapper;
import com.puvar.cloudservice.domain.MgTask;
import com.puvar.cloudservice.domain.MgTaskTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/10/25
 * @version : 1.0
 */
@Service
public class MgTaskExecutor extends TaskExecutor {

    @Autowired
    private MgTaskMapper mgTaskMapper;

    @Override
    public int getTaskMaxNum() {
        return MgTaskConstants.DEF_MAX_TASK_NUM;
    }

    @Override
    public int getLoopInterval() {
        return MgTaskConstants.DEF_TASK_LOOP_INTERVAL * 1000;
    }

    @Override
    public List<Task> refreshWaitTask() {
        List<MgTask> mgTasks = mgTaskMapper.selectAll();
        if (CollectionUtils.isEmpty(mgTasks)) {
            return null;
        }
        List<Task> list = Lists.newArrayListWithExpectedSize(mgTasks.size());
        for (MgTask mgTask : mgTasks) {
            MgTaskTask taskTask = new MgTaskTask();
            taskTask.setParams(parseParam(mgTask.getTaskParam()));
            taskTask.setTaskId(mgTask.getTaskId());
            list.add(taskTask);
        }
        return list;
    }

    @Override
    public void checkRunningTask() {

    }

    private Map<String,Object> parseParam(String param){
        JSONObject json = JSONObject.parseObject(param);
        return json.toJavaObject(Map.class);
    }
}
