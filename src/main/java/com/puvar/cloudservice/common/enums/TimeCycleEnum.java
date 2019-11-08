package com.puvar.cloudservice.common.enums;

/***
 * 周期枚举类
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
public enum TimeCycleEnum {

    year(4), day(3), week(2), month(1);

    int value;

    TimeCycleEnum(int value) {
        this.value = value;
    }

    public static int valueOf(TimeCycleEnum cycle) {
        return cycle.value;
    }

}
