package com.puvar.cloudservice.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MgTask implements Serializable {
    /**
    * 主键
    */
    private Long id;

    /**
    * 任务Id
    */
    private String taskId;

    /**
    * 任务参数
    */
    private String taskParam;

    /**
    * 创建时间
    */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}