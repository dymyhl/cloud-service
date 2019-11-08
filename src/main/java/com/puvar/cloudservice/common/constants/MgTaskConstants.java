package com.puvar.cloudservice.common.constants;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/10/25
 * @version : 1.0
 */
public class MgTaskConstants {
    // 每个实例默认同时执行的任务数量
    public static final int DEF_MAX_TASK_NUM=2;
    // 调度器轮询间隔，单位s
    public static final int DEF_TASK_LOOP_INTERVAL = 10;
    // 默认值：异步任务心跳超时时间，单位s
    public static final int DEF_TASK_HEART_BEAT_OVERTIME = 60;
}
