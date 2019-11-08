package com.puvar.cloudservice.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/***
 * 日志对象
 * @Auther: dingyuanmeng
 * @Date: 2019/9/25
 * @version : 1.0
 */
@Data
@Accessors
public class ManageLog {

    private Integer id;
    private String logId;
    private String system;
    private String requestUrl;
    private String requestMethod;
    private String operateDesc;
    private String paramValue;
    private String resultValue;
    private String exceptionValue;
    private int timeLength;
}
