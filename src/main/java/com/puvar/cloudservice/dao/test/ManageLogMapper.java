package com.puvar.cloudservice.dao.test;

import com.puvar.cloudservice.domain.ManageLog;

import java.util.List;

/***
 * @Auther: dingyuanmeng
 * @Date: 2019/10/11
 * @version : 1.0
 */
public interface ManageLogMapper {
    List<ManageLog> findAll();

    void save(ManageLog manageLog);
}
