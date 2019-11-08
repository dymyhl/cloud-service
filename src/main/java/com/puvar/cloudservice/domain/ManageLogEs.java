package com.puvar.cloudservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;


/***
 * 日志对象
 * @Auther: dingyuanmeng
 * @Date: 2019/9/25
 * @version : 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
@Document(indexName = "hello-server", type = "manage_log")
public class ManageLogEs implements Serializable {

    @Id
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
